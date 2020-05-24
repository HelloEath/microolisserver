package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/22  16:39
 */
@Data
@Entity
@SQLDelete(sql = "update t_region set del = 1 where id = ?")
@Where(clause = "del = 0")
public class Region extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;


    @Column(length = 255)
    @ApiModelProperty(value = "地区名称")
    private String regionName;
}
