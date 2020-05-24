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
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 权限
 * @author DELL
 *
 */
@Entity
@ApiModel(value = "权限")
@SQLDelete(sql = "delete from t_permission where id = ?")
@Where(clause = "del = 0")
@Data
public class Permission extends BaseEntity {

    @ApiModelProperty(value = "权限名称")
    @Column(length = 100)
    private String name;

    @ApiModelProperty(value = "权限描述")
    @Column(length = 100)
    private String description;
    
    @ApiModelProperty(value = "父节点ID")
    private Long parentId;

	@OneToMany(mappedBy="parentId",fetch= FetchType.EAGER)
	private List<Permission> children;

	private String path;

	/**
	 * 图标
	 */
	private String iconCls;

	@ApiModelProperty(value = "权限显示顺序")
	@Column(nullable = false, columnDefinition = "integer default 1")
	private Integer sorting;



	
}
