package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/10/27  18:51
 */
@Entity
@Data
@SQLDelete(sql = "update t_olis set del = 1 where id = ?")
@Where(clause = "del = 0")
public class Olis extends BaseEntity {

    @ManyToOne
    @ApiModelProperty(value = "图片素材")
    private ImgMaterial imgMaterial;

}
