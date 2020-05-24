package com.hello.adminservice.util;

import com.hello.common.entity.system.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * 使用AOP统一处理Web请求日志
 * Created by lxw on 2018/10/17.
 */
//声明是个切面
@Aspect
@Component
@Order(1)
public class LoggerUtils {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//ThreadLocal<Long> startTime = new ThreadLocal<>();
	
	//声明一个切点  里面是 execution表达式
	@Pointcut("execution(public * com.hello.adminservice.controller..*.*(..)) && !execution(public * com.hello.adminservice.controller.*LoginController.*(..)) && !execution(public * com.hello.adminservice.controller.CommonController.systemLog(..))")
	public void controllerLog(){}
	
	//请求method前打印内容
	@Before("controllerLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		//开始时间
		//startTime.set(System.currentTimeMillis());
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		
		//当前操作用户
		User user = PasswordUtils.currentUser();
		if (user==null){
			return;
		}
		//获取request
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = (HttpServletRequest) requestAttributes.getRequest();
		//打印下请求内容
		logger.info("登陆账号=" + user.getUsername() + ","
				+ "用户名=" + user.getName() + ","
				+ "地区= "+","
				+ "URL=" + request.getRequestURL().toString() + ","
				+ "IP=" + getRemoteIP(request) + ","
				+ "类的方法名=" + joinPoint.getSignature() + ","
				+ "方法参数=" + Arrays.toString(joinPoint.getArgs()),  user.getRegion()==null? "无":user.getRegion().getRegionName(), user.getName(), request.getRequestURL().toString(), Arrays.toString(joinPoint.getArgs()));
	}
	
	//在方法执行完后打印返回内容
	@AfterReturning(returning = "result", pointcut = "controllerLog()")
	public void doAfterReturning(Object result) throws Throwable {
		//logger.info("============= 返回内容 start =============");
		//logger.info("RESPONSE : " + JSON.toJSONString(result));
		//logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
		//logger.info("============= 返回内容 end ===============");
	}
	
	//在切点后抛出异常进行处理
	@AfterThrowing(throwing = "e", pointcut = "controllerLog()")
	public void dothrows(JoinPoint joinPoint, Exception e) {
		//当前操作用户
		User user = PasswordUtils.currentUser();
		if (user==null){
			logger.error(e.getMessage(), e);
			return;
		}
		//获取request
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = (HttpServletRequest) requestAttributes.getRequest();
		logger.info(request.getRequestURL().toString() + "方法异常时执行...",  user.getRegion()==null? "无":user.getRegion().getRegionName(), user.getName(), request.getRequestURL().toString(), Arrays.toString(joinPoint.getArgs()));
		logger.error(e.getMessage(),  user.getRegion()==null? "无":user.getRegion().getRegionName(), user.getName(), request.getRequestURL().toString(), Arrays.toString(joinPoint.getArgs()));
		logger.error(e.getMessage(), e);
	}
	
	//获取真实的IP地址
	public String getRemoteIP(HttpServletRequest request) {
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}  
}
