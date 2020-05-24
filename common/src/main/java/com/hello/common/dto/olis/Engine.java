package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/10/27  18:49
 */
@Entity
@Data
@SQLDelete(sql = "update t_engine set del = 1 where id = ?")
@Where(clause = "del = 0")
public class Engine extends BaseEntity {


    @ManyToOne
    @ApiModelProperty(value = "年限")
    private Year year ;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;


    @ManyToOne
    @ApiModelProperty(value = "发动机类型")
    private EngineType engineType;

    @ManyToOne
    @ApiModelProperty(value = "品牌")
    private Brand brand ;

    @ManyToOne
    @ApiModelProperty(value = "一级车型")
    private OneLevelCarType one ;

    @ManyToOne
    @ApiModelProperty(value = "二级车型")
    private TwoLevelCarType two ;

    @ManyToOne
    @ApiModelProperty(value = "三级车型")
    private ThreeLevelCarType three ;

    @ManyToOne
    @ApiModelProperty(value = "图片素材")
    private ImgMaterial imgMaterial ;

    @Transient
    @ApiModelProperty(value = "sae集合")
    private List<SaeDesc> saeDescList;
}
