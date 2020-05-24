package com.hello.common.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hello.common.dto.olis.Region;
import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户
 * Created by Hzh on 2018/6/1.
 */
@Entity
@ApiModel(value = "系统用户User")
@SQLDelete(sql = "update t_user set del = 1 where id = ?")
@Where(clause = "del = 0")
@Data
public class User extends BaseEntity  {

    @ApiModelProperty(value = "登录名")
    @Column(length = 100)
    private String username;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;

    @ApiModelProperty(value = "编号")
    @Column(length = 100)
    private String uCode;

    @Column(length = 1000)
    private String password;

    @ApiModelProperty(value = "用户名")
    @Column(length = 100)
    private String name;

    @ApiModelProperty(value = "手机号")
    @Column(length = 100)
    private String phone;

    @ApiModelProperty(value = "email")
    @Column(length = 100)
    private String email;

    @ApiModelProperty(value = "状态,0启用 1禁用")
    @Column(length = 1, nullable = false, columnDefinition = "int default 0")
    private int status;



    @ApiModelProperty(value = "用户角色")
    @ManyToOne(fetch= FetchType.EAGER)
    private Role role;

    @ApiModelProperty(value = "所属地区")
    @ManyToOne
    private Region region;

    /**
     * 是否可以删除禁用(超级管理员设置1 禁用操作删除)
     */
    @Column(length = 1, nullable = false, columnDefinition = "int default 0")
    //@JsonIgnore
    private int op;
    



    
}
