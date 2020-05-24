package com.hello.common.entity.system;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 系统参数
 * Created by lxw on 2019/05/09.
 */
@Entity
@ApiModel(value = "系统参数表")
@SQLDelete(sql = "update t_system_params set del = 1 where id = ?")
@Where(clause = "del = 0")
public class SystemParams extends BaseEntity {
	/**
     * 参数名
     */
	@ApiModelProperty(value = "参数名")
	@Column(length = 200)
    private String paramName;
    
    /**
     * 参数值
     */
	@ApiModelProperty(value = "参数值")
	@Column(length = 200)
    private String paramValue;
    
    /**
     * 参数描述
     */
	@ApiModelProperty(value = "参数描述")
	@Column(length = 400)
    private String paramDesc;

	public String getParamName() {
		return paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	
}
