package com.hello.common.entity.system;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * 角色
 * Created by Hzh on 2018/7/5.
 */
@Entity
@ApiModel(value = "用户角色")
@SQLDelete(sql = "update t_role set del = 1 where id = ?")
@Where(clause = "del = 0")
@Data
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名称")
    @Column(length = 100)
    private String name;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;

    @ApiModelProperty(value = "角色描述")
    @Column(length = 100)
    private String description;

    @ApiModelProperty(value = "角色唯一标识,不可修改,用于判断角色权限,写入代码")
    @Column(length = 100)
    private String code;

    
    @ApiModelProperty(value = "权限集合")
    @ManyToMany(fetch= FetchType.EAGER)
    private List<Permission> permissions;
    

}
