package com.hello.apigatewayservice.util;

import com.hello.apigatewayservice.Exception.ApplicationException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * xls文件读取解析工具,XSSF方式,适用于2007以上版本
 * Created by hzh on 2018/7/15.
 */
public class PoiUtils {

    public static ArrayList<ArrayList<String>> xlsReader(File file, int... args) throws IOException {
        //返回二维字符串数组
        //读取xlsx文件
        Workbook wb = null;
        //寻找目录读取文件
        InputStream is = new FileInputStream(file);
        wb = new XSSFWorkbook(is);
        is.close();

        if (wb == null) {
            System.out.println("未读取到内容,请检查路径！");
            return null;
        }

        ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
        //遍历xlsx中的sheet
        for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
            Sheet sheet = wb.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            // 对于每个sheet，读取其中的每一行
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;
                ArrayList<String> curarr = new ArrayList<String>();
                for (int columnNum = 0; columnNum < args.length; columnNum++) {
                    Cell cell = row.getCell(args[columnNum]);
                    curarr.add(Trim_str(getValue(cell)));
                }
                ans.add(curarr);
            }
        }
        return ans;
    }

    /**
     * 适用于2003、2007以上版本
     * @param file
     * @param args
     * @return
     * @throws IOException
     */
    public static ArrayList<ArrayList<String>> excelReader(MultipartFile file, int... args) throws Exception {
        //返回二维字符串数组
        //读取excel文件
    	String fileName = file.getOriginalFilename();
    	if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new ApplicationException("上传文件格式不正确！");
        }
    	if (file.isEmpty()) {
    		throw new ApplicationException("文件内容不能为空！");
    	}
    	Workbook wb = null;
    	InputStream is = null;
        //寻找目录读取文件
    	is = file.getInputStream();
		//判断Excel文件的版本
		if (fileName.endsWith("xlsx")) {
			wb = new XSSFWorkbook(is);
		}
		if (fileName.endsWith("xls")) {
			wb = new HSSFWorkbook(is);
		}
		if (wb == null) {
			throw new ApplicationException("未读取到内容,请检查路径！");
		}
        is.close();

        //文件行数
        int count = 1;
        ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
        //遍历xlsx中的sheet
        for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
            Sheet sheet = wb.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            int lastRowNum = sheet.getLastRowNum();
            count = count + lastRowNum;
            System.out.println("count====" + count);
            //如果行数超过一万，则导入失败
            if (count > 10000) {
            	throw new ApplicationException("导入失败，导入条数大于1万条！");
            }
            // 对于每个sheet，读取其中的每一行
            for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;
                ArrayList<String> curarr = new ArrayList<String>();
                for (int columnNum = 0; columnNum < args.length; columnNum++) {
                    Cell cell = row.getCell(args[columnNum]);
                    String str = Trim_str(getValue(cell));
                    //校验格式
                    if (columnNum == 0) {
                    	if (StringUtil.isEmpty(str)) {
                    		throw new ApplicationException("导入失败，姓名不能为空！");
                    	}
                    } else if (columnNum == 1) {
                    	if (!validatePhoneNumber(str)) {
                    		throw new ApplicationException("导入失败，手机号码格式错误！");
                    	}
                    } else if (columnNum == 2) {
                    	if (!validateEcifNo(str)) {
                    		throw new ApplicationException("导入失败，Ecif客户号格式错误！");
                    	}
                    }
                    curarr.add(str);
                }
                ans.add(curarr);
            }
        }
        if (wb != null) {
			try {
				wb.close();
			} catch (IOException e) {
				throw e;
			}
		}

        return ans;
    }

    //判断后缀为xlsx的excel文件的数据类
    @SuppressWarnings("deprecation")
    private static String getValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            double cur = cell.getNumericCellValue();
            long longVal = Math.round(cur);
            Object inputValue = null;
            if (Double.parseDouble(longVal + ".0") == cur)
                inputValue = longVal;
            else
                inputValue = cur;
            return String.valueOf(inputValue);
        } else if (cell.getCellType() == cell.CELL_TYPE_BLANK || cell.getCellType() == cell.CELL_TYPE_ERROR) {
            return "";
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    //字符串修剪  去除所有空白符号 ， 问号 ， 中文空格
    static private String Trim_str(String str) {
        if (str == null)
            return null;
        return str.replaceAll("[\\s\\?]", "").replace("　", "");
    }
    //校验手机号
    private static boolean validatePhoneNumber(String phoneNumber) {
    	//String telRegex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    	String telRegex = "^\\d{11}$";
    	return !StringUtils.isEmpty(phoneNumber) && phoneNumber.matches(telRegex);
    }

    //校验Ecif客户号
    private static boolean validateEcifNo(String ecifNo) {
    	String telRegex = "^\\d{18}$";
    	return !StringUtils.isEmpty(ecifNo) && ecifNo.matches(telRegex);
    }


    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String []title, List<String[]> data, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i = 0;i < title.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        try{
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                HSSFRow rowData = sheet.createRow(rowNum);
                for (int j = 0; j < data.get(i).length; j++) {
                	rowData.createCell(j).setCellValue(data.get(i)[j]);
                }
                rowNum++;
            }
            //System.out.println("表格赋值成功！");
            // 冻结窗格
            wb.getSheet(sheetName).createFreezePane(0, 1, 0, 1);
        }catch (Exception e){
        	System.out.println("表格赋值失败！");
            e.printStackTrace();
        }

        return wb;
    }


    public static void main(String[] args) {
    	boolean flag = validatePhoneNumber("12542698411");
    	boolean flag2 = validateEcifNo("15542698411666666f");
    	System.out.println("tel====" + flag);
    	System.out.println("ecifNO====" + flag2);
//        String path = "/Users/hzh/work/workspace2/sszserver/upload/temp/1.xlsx";
//        File file = new File(path);
//        System.out.println("-------------");
//        System.out.println(file.exists());
//        try {
//            ArrayList<ArrayList<String>> sheets = PoiUtils.xlsReader(file,0,1,2,3);
//            for (ArrayList<String> rows:sheets) {
//                for (String content:rows) {
//                    //System.out.print(content+"  |  ");
//                }
//                System.out.print(rows.get(0));
//                System.out.println();
//            }
//            System.out.println("-----------");
//        }catch (IOException e){
//            e.printStackTrace();
//        }

//        HashSet<Long> channes = new LinkedHashSet<>();
//        channes.add(1l);
//        channes.add(1l);
//        channes.add(1l);
//        channes.add(1l);
//        channes.add(1l);
//        channes.add(2l);
//        channes.add(2l);
//        channes.add(2l);
//        channes.add(3l);
//        System.out.println(channes.size());
    }
}

