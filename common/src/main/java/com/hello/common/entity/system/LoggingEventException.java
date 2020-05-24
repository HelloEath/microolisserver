package com.hello.common.entity.system;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统日志异常信息
 * Created by lxw on 2019/03/26.
 */
@Entity
@Table(name = "logging_event_exception")
@IdClass(LoggingEventException.class)
@ApiModel(value = "系统日志异常表")
public class LoggingEventException implements Serializable {
	
	@Id
	@Column(name="event_id", length = 10)
    private Long eventId;
	
    @Column(name="i")
    private int i;
    
    @Column(name="trace_line", length = 254)
    private String traceLine;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getTraceLine() {
		return traceLine;
	}

	public void setTraceLine(String traceLine) {
		this.traceLine = traceLine;
	}
    
    
}
