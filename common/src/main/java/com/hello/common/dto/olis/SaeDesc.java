package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/22  16:44
 */
@Entity
@Data
@SQLDelete(sql = "update t_sae_desc set del = 1 where id = ?")
@Where(clause = "del = 0")
public class SaeDesc extends BaseEntity {

    @Column(length = 255)
    @ApiModelProperty(value = "api名称")
    private String apiName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ApiModelProperty(value = "描述1")
    private String desc1;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ApiModelProperty(value = "描述2")
    private String desc2;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ApiModelProperty(value = "描述3")
    private String desc3;

    @Column(length = 255)
    @ApiModelProperty(value = "行驶距离,1:不限制，2:超过十五至二十万公里 ，3:十五至二十万公里一下")
    private int runKm=1;

    @ApiModelProperty(value = "发动机类型")
    @ManyToOne
    private EngineType engineType;

    @ApiModelProperty(value = "发动机")
    private Long engineId;

    @ApiModelProperty(value = "三级车型")
    @ManyToOne
    private ThreeLevelCarType three ;

    @ApiModelProperty(value = "油品")
    @ManyToOne
    private ImgMaterial imgMaterial;

    @Transient
    private Olis olisObject;

    @Transient
    private ImgBase imgBase;

}
