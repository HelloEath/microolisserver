package com.hello.common.entity.system;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

/**
 * 系统日志
 * Created by lxw on 2019/03/20.
 */
@Entity
@Table(name = "logging_event")
@ApiModel(value = "系统日志表")
@Data
public class SystemLog {
	@Id
    @Column(name="event_id", length = 10)
    private Long eventId;
    
    @Column(name="timestmp", length = 20)
    private Long timestmp;
    
    @Column(name="formatted_message", length = 4000)
    private String formattedMessage;
    
    @Column(name="level_string", length = 254)
    private String levelString;
    
    @Column(name="arg0", length = 254)
    private String arg0;//登陆账号
    
    @Column(name="arg1", length = 254)
    private String arg1;//用户名
    
    @Column(name="arg2", length = 254)
    private String arg2;//URL
    
    @Column(name="arg3", length = 254)
    private String arg3;//方法参数

	@Column(name="caller_line", length = 254)
	private String callerLine;//方法参数

    @Column(name="caller_method", length = 254)
    private String callerMethod;//方法参数

    @Column(name="caller_class", length = 254)
    private String callerClass;//方法参数


    @Column(name="caller_filename", length = 254)
    private String callerFilename;//方法参数

    @Column(name="logger_name", length = 254)
    private String loggerName;//方法参数

    @Column(name="thread_name", length = 254)
    private String threadName;//方法参数


    @Column(name="reference_flag", length = 254)
    private String referenceFlag;//方法参数
	public SystemLog() {
		super();
	}
    
}
