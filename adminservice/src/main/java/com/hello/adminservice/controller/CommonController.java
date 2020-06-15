package com.hello.adminservice.controller;

import com.hello.common.util.Result;
import com.hello.common.entity.system.LoggingEventException;
import com.hello.common.entity.system.SystemLog;
import com.hello.common.entity.system.SystemParams;
import com.hello.common.entity.system.UploadFile;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Hzh on 2018/6/1.
 */
@Api(value = "通用接口",description = "CommonController,处理初始化以及一些通用api")
@RequestMapping(path = "/common")
public interface CommonController {


	@ApiOperation(value = "服务端枚举类型集合[注意,该接口在生产环境并不开放,仅供参考数值.]", notes = "返回所有服务端枚举类型集合,前端提交枚举类型数据需和服务端保持一致,故列举出来供参考.")
	@RequestMapping(path = "/enums", method = RequestMethod.GET)
	Result<List> enums();

	@ApiOperation(value = "获取所有地区", notes = "返回所有地区列表")
	@RequestMapping(path = "/getRegions", method = RequestMethod.GET)
	Result<List> getAllRegions();

    @ApiOperation(value = "获取年限", notes = "返回所有年限列表")
    @RequestMapping(path = "/getYears", method = RequestMethod.GET)
    Result<List> getAllYears();

	@ApiOperation(value = "上传(完成)", notes = "文件上传,上传至临时目录")
	@RequestMapping(value = "/upload", produces = {"application/json"}, method = RequestMethod.POST)
	Result<UploadFile> upload(@RequestParam("file") MultipartFile file);

	@ApiOperation(value = "下载(完成)", notes = "文件下载")
	@ApiImplicitParam(name = "code", value = "uploadfile的唯一编码", required = true, dataType = "String", paramType = "path")
	@RequestMapping(value = "/download/{code}", method = RequestMethod.GET)
	void download(@PathVariable String code, HttpServletResponse res);

	@ApiOperation(value = "url访问静态资源(完成)", notes = "图片,文件等访问,将会转发")
	@ApiImplicitParam(name = "code", value = "uploadfile的唯一编码", required = true, dataType = "String", paramType = "path")
	@RequestMapping(value = "/static/{code}", method = RequestMethod.GET)
	void staticResource(@PathVariable String code, HttpServletResponse response);


	@ApiOperation(value = "登出", notes = "")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	void logout(HttpServletRequest request, HttpServletResponse response);

	@ApiOperation(value = "获取系统日志集合(完成)", notes = "根据查询条件来获取系统集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间，格式：时间戳", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间，格式：时间戳", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "user", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "接口名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "args", value = "方法参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "levelString", value = "日志级别", dataType = "String", paramType = "query")
    })
    @RequestMapping(path = "/system-log", method = RequestMethod.GET)
    Result<Page<SystemLog>> systemLog(Integer pageNo, Long startDate, Long endDate, String user, String url, String args, String levelString);
	
	@ApiOperation(value = "删除系统日志集合(完成)", notes = "根据查询条件来删除系统集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间，格式：时间戳", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间，格式：时间戳", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "user", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "接口名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "args", value = "方法参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "levelString", value = "日志级别", dataType = "String", paramType = "query")
    })
    @RequestMapping(path = "/del-system-log", method = RequestMethod.DELETE)
    Result<String> delSystemLog(Long startDate, Long endDate, String user, String url, String args, String levelString);

	@ApiOperation(value = "根据eventId获取日志异常信息(完成)", notes = "根据eventId获取日志异常信息(子活动)")
    @ApiImplicitParam(name = "eventId", value = "主活动id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(path = "/logging-event-exception/{eventId}", method = RequestMethod.GET)
    Result<List<LoggingEventException>> getLoggingEventException(@PathVariable Long eventId);
	
	@ApiOperation(value = "获取所有的系统参数(完成)", notes = "获取所有的系统参数")
	@RequestMapping(path = "/getSystemParams", method = RequestMethod.GET)
	Result<List<SystemParams>> getSystemParams();
	
	@ApiOperation(value="保存/更新系统参数(完成)", notes="保存/更新系统参数(完成)")
    @RequestMapping(path = "/system-params",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<SystemParams> saveSystemParam(@RequestBody @ApiParam(name = "系统参数systemParams", value = "传入json格式", required = true) SystemParams systemParams);
	

	@ApiOperation(value = "下载resources下的模版(完成)", notes = "下载resources下的模版")
	@ApiImplicitParam(name = "fileName", value = "模版文件名称", required = true, dataType = "String", paramType = "query")
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
	void downloadTemplate(String fileName, HttpServletResponse res);
}
