package com.hello.apigatewayservice.util;

import com.hello.common.dto.DownloadFile;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by hzh on 2018/7/14.
 */
public class FileUtils {
    /**
     * 删除物理文件
     * @param file
     * @return
     */
    public static boolean del(File file){
        return org.apache.commons.io.FileUtils.deleteQuietly(file);
    }


    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    public static boolean upload(MultipartFile file, String path) {
        boolean result = false;
        if (!file.isEmpty()) {
            //文件路径策略,对应目录下,每天为子目录,文件名加uuid前缀避免重复
            File saveFile = new File(path);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(org.apache.commons.io.FileUtils.openOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                result = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (out!=null){
                    try {
                        out.flush();
                        out.close();
                    }catch (Exception e){
                    }
                }
            }
        }
        return result;
    }


    /**
     * 从网络保存文件到本地
     *
     * @param file
     * @return
     */
    public static boolean uploadFromWeb(String urlString,String path,int i) {
        boolean result = false;
        i++;

        if (i>5){
            return false;
        }
        BufferedOutputStream out = null;
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5*1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File saveFile = new File(path);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            out = new BufferedOutputStream(org.apache.commons.io.FileUtils.openOutputStream(saveFile));
            while ((len = is.read(bs)) != -1) {
                out.write(bs, 0, len);
            }
            out.flush();
            out.close();
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("保存图片异常,重试次数："+i+",imgurl="+urlString);
            uploadFromWeb(urlString,path,i);
            //e.printStackTrace();
        }finally {
            if (out!=null){
                try {
                    out.flush();
                    out.close();
                }catch (Exception e){
                }
            }
        }
        return result;
    }



    /**
     * 读文件到流
     * @param os
     * @param file
     * @return
     */
    public static boolean read(OutputStream os,File file){
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream( org.apache.commons.io.FileUtils.openInputStream(file));

            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  false;
        }
    }
    
    public static boolean read(OutputStream os, InputStream in){
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(in);

            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            return  false;
        }
    }

    /**
     * 多文件上传
     *
     * @param request
     * @return
     */
    public String uploadFiles(HttpServletRequest request) throws IOException {
        File savePath = new File(request.getSession().getServletContext().getRealPath("/upload/"));
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    return "第 " + i + " 个文件上传有错误" + e.getMessage();
                }
            } else {
                return "第 " + i + " 个文件为空";
            }
        }
        return "所有文件上传成功";
    }

    public static String getDictionaryString(Resource resource) throws IOException{
        //当需要打成jar包运行时,需要用getinputStream来实现,不能直接使用file,因为操作系统无法直接获取jar包内文件路径
        String dictionaryString = new String(IOUtils.toString(resource.getInputStream(), Charset.forName("utf-8")));
//        String dictionaryString = new String(FileUtils.readFileToString(resource.getFile(),Charset.forName("utf-8")));
        return dictionaryString;
    }
    
    public static String fileToZip(List<DownloadFile> files, String fileName, String filePath) {
    	FileInputStream fis = null;
    	BufferedInputStream bis = null;
    	FileOutputStream fos = null;
    	ZipOutputStream zos = null;
    	String path = filePath;
    	try {
    		File zipFile = new File(filePath);
        	// 如果存在该zip文件，则删除
            zipFile.deleteOnExit();
            if (!zipFile.getParentFile().exists()) {
            	zipFile.getParentFile().mkdirs();
            }
            // 创建一个新文件
            zipFile.createNewFile();
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024 * 10];
            for (DownloadFile downloadFile: files) {
                File subFile = downloadFile.getFile();
                //如果文件不存在，则不添加到zip包中
                if (!subFile.exists()) {
                    continue;
                }
                //获取文件名
                String subFileName =  subFile.getName();
                //创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(subFileName);
                zos.putNextEntry(zipEntry);
                //读取待压缩的文件并写进压缩包里
                fis = new FileInputStream(subFile);
                bis = new BufferedInputStream(fis, 1024 * 10);
                int read = 0;
                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                    zos.write(bufs, 0, read);
                }
            }
    	} catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //关闭流
            try {
                if (null != bis) bis.close();
                if (null != zos) zos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }    	        
    	return path;
    }
    
    /**
     * 创建文件并把内容写进去
     * @param inFileName 文件名
     * @param content 内容
     * @return
     */
    public static boolean createFile(String inFileName, String content) {
    	if (!StringUtils.isEmpty(inFileName)) {
    		BufferedWriter out = null;
    		try {
    			File createFile = new File(inFileName);
                if (!createFile.getParentFile().exists()) {
                	createFile.getParentFile().mkdirs();
                }
                if (createFile.exists()) {
                	return false;
                } else {
                	createFile.createNewFile();
                }
                //out = new BufferedWriter(new FileWriter(createFile));
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(createFile), "UTF-8"));
                
                out.write(content); // \r\n即为换行
                out.flush(); 
                out.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
                if (out!=null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                    }
                }
            }
    	}
    	return true;
    }
    
    /**
     * 把文件压缩成.gz文件
     * @param inFileName
     * @return
     */
    public static String compressFile(String inFileName) {
    	
    	String outFileName = inFileName + ".gz";
    	File outFile = new File(outFileName);
    	if (outFile.exists()) {
    		return null;
    	}
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(inFileName), "UTF-8"));
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the inFile..."+inFileName);           
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outFileName)));
        } catch (IOException e) {
        	System.out.println("Could not find the outFile..."+outFileName);
        }
        try {
        	int c;
            while ((c = in.read()) != -1) {
                /* 
                 * 注，这里是压缩一个字符文件，前面是以字符流来读的，不能直接存入c，因为c已是Unicode
                 * 码，这样会丢掉信息的（当然本身编码格式就不对），所以这里要以UTF-8来解后再存入。
                 */
                out.write(String.valueOf((char) c).getBytes("UTF-8"));
            }
            in.close();
            out.close();
            System.out.println("压缩文件成功...");
            //删除txt文件
            File txtFile = new File(inFileName);
            if (txtFile.exists()) {
            	txtFile.delete();
            	System.out.println("删除txt文件成功...");
            }
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != in) in.close();
                if (null != out) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outFile.getName();
    }
}
