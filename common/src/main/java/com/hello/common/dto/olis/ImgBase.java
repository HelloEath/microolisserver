package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/2  11:46
 */
@Entity
@Data
@SQLDelete(sql = "update t_img_base set del = 1 where id = ?")
@Where(clause = "del = 0")
public class ImgBase extends BaseEntity {


    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;

    @ManyToOne
    private ImgMaterial imgMaterial;
}
