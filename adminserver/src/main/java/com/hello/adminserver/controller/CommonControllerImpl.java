package com.hello.adminserver.controller;

import  com.hello.common.controller.common.BaseController;
import com.hello.adminserver.service.CommonService;
import com.hello.adminserver.service.FileService;
import com.hello.adminserver.service.InitService;
import com.hello.common.dto.DownloadFile;
import com.hello.common.dto.Enum4show;
import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.Year;
import com.hello.common.entity.system.LoggingEventException;
import com.hello.common.entity.system.SystemLog;
import com.hello.common.entity.system.SystemParams;
import com.hello.common.entity.system.UploadFile;
import com.hello.common.util.*;
import com.hello.common.util.enumeration.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzh on 2018/6/22.
 */
@RestController
@RequestMapping(path = "/common")
public class CommonControllerImpl extends BaseController implements CommonController {
    @Autowired
    FileService fileService;

    @Autowired
    CommonService commonService;
    @Autowired
    InitService initService;

    //@Value("classpath:templates/excelTemplate/名单导入文件样例.xls")
    //Resource template;


    @Override
    public Result<UploadFile> upload(@RequestParam("file") MultipartFile file) {
        UploadFile file1 = fileService.uploadTemp(file);
        return ResultUtil.success(file1);
    }

    @RequestMapping(value = "/login1", produces = {"application/json"}, method = RequestMethod.POST)
    public void test(@RequestBody Map map){
        String username = "";
    }

    @Override
    public void download(@PathVariable String code, HttpServletResponse res) {
        DownloadFile downloadFile = fileService.getFile(code);

        String filename = downloadFile.getUploadFile().getOriginFileName();

        /* 根据request的locale 得出可能的编码，中文操作系统通常是gb2312 */
        try {
            filename = new String(filename.getBytes("UTF-8"), "ISO_8859_1");
        }catch (Exception e){
            e.printStackTrace();
        }
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + filename);

        if (downloadFile.getFile()!=null&&downloadFile.getFile().exists()){
            try {
                FileUtils.read(res.getOutputStream(),downloadFile.getFile());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 初始化
     * @return
     * @throws IOException
     */
    @GetMapping(path = "/init")
    public String init(String systemType) throws IOException {
        return initService.init(systemType);
    }


    @Override
	public Result<List> getAllRegions() {
		List<Region> regionList = commonService.findAllRegion();
		Result<List> result = new Result<>();
		result.setData(regionList);
		result.setMsg("查询结果条目数:"+regionList.size());
		return result;
	}
    @Override
    public Result<List> getAllYears() {
        List<Year> regionList = commonService.findAllYear();
        Result<List> result = new Result<>();
        result.setData(regionList);
        result.setMsg("查询结果条目数:"+regionList.size());
        return result;
    }

    @Override
    public Result<List> enums() {
        List<Enum4show> list = new ArrayList<>();
        Set<Class<?>> set = ReflectUtils.getClasses(Commons.class.getPackage().getName());
        for (Class c : set
                ) {
            Enum4show enum4show = new Enum4show();
            enum4show.setEnumName(ReflectUtils.enumDesc(c)+":"+c.getSimpleName());
            enum4show.setMap(ReflectUtils.enumValues(c));
            list.add(enum4show);
        }
        return ResultUtil.success(list);
    }

    /**
     * 请求转发
     * @param code
     * @param response
     */
    @Override
    public void staticResource(@PathVariable String code, HttpServletResponse response) {
        try {
            String url = fileService.url(code);
            response.sendRedirect(url);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            response.sendRedirect("/login?logout");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 获取系统日志集合
     * @param pageNo
     * @param startDate
     * @param endDate
     * @param user
     * @param url
     * @param args
     * @return
     */
    @Override
    public Result<Page<SystemLog>> systemLog(Integer pageNo, Long startDate, Long endDate, String user, String url, String args, String levelString) {
        Page<SystemLog> page = commonService.searchSystemLog(PageRequestUtil.build(pageNo,20,"eventId"),startDate,endDate,user,url,args,levelString);
        return ResultUtil.success(page);
    }

    /**
     * 根据eventId获取日志异常信息
     * @param eventId
     */
	@Override
	public Result<List<LoggingEventException>> getLoggingEventException(@PathVariable Long eventId) {
		return ResultUtil.success(commonService.getLoggingEventException(eventId));
	}

	/**
	 * 获取所有的系统参数
	 * @param
	 * @return
	 */
    @Override
	public Result<List<SystemParams>> getSystemParams() {
		List<SystemParams> systemParamsList = commonService.findSystemParams();
		return ResultUtil.success(systemParamsList);
	}

    @Override
	public Result<SystemParams> saveSystemParam(@RequestBody SystemParams systemParams) {
		return ResultUtil.success(commonService.saveSystemParam(systemParams));
	}



    /**
     * 下载resources下的模版
     */
    @Override
    public void downloadTemplate(String fileName, HttpServletResponse res) {
    	try {
    		//File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "templates/excelTemplate/" + fileName);
    		/* 根据request的locale 得出可能的编码，中文操作系统通常是gb2312 */
//    		fileName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1");
//
//    		res.setHeader("content-type", "application/octet-stream");
//            res.setContentType("application/octet-stream");
//            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

    		//File file = template.getFile();
    		//File file = ResourceUtils.getFile("classpath:templates/excelTemplate/" + fileName);
    		//读取文件
    		ClassPathResource resource = new ClassPathResource("templates/excelTemplate/" + fileName);

    		res.setContentType("application/vnd.ms-excel");
    		res.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    		res.addHeader("charset", "utf-8");
    		res.addHeader("Pragma", "no-cache");
	        String encodeName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
	        res.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);


            if (resource != null && resource.exists()){
            	FileUtils.read(res.getOutputStream(), resource.getInputStream());
            }
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除系统日志集合
     * @param startDate
     * @param endDate
     * @param user
     * @param url
     * @param args
     * @return
     */
    @Override
    public Result<String> delSystemLog(Long startDate, Long endDate, String user, String url, String args, String levelString) {
    	return ResultUtil.success(commonService.delSystemLog(startDate,endDate, user,url,args, levelString));
    }


}
