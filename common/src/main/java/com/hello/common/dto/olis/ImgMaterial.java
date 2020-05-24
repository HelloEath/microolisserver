package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import com.hello.common.entity.system.UploadFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/22  16:04
 */
@Data
@Entity
@SQLDelete(sql = "update t_img_material set del = 1 where id = ?")
@Where(clause = "del = 0")
public class ImgMaterial extends BaseEntity {
    /*
    1:发动机图片
    2:保护油图片
    */
    @Column(length = 1)
    @ApiModelProperty(value = "素材类型")
    private int materialType;

    @Column(length = 255)
    @ApiModelProperty(value = "素材类型")
    private String materialTypeName;

    @Column(length = 255)
    @ApiModelProperty(value = "素材名称")
    private String materialName;

    @Column(length = 255)
    @ApiModelProperty(value = "语音说明")
    private String soundDesc;

    @ManyToOne
    @ApiModelProperty(value = "图片")
    private UploadFile uploadFile;


    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "公里数")
    private String materialKm;

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "销量")
    private String materialSales;

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "价格")
    private String materialPrize;

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "Api保护油说明")
    private String materialApiDesc;

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "排序")
    private int materialRank;

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "sae")
    private String materialSae;

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "保护类型,默认无")
    private String materialProtectedType="1";

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "油品牌子")
    private String materialOlisBrandType;

    //当materialType==2时有值
    @Column(length = 255)
    @ApiModelProperty(value = "油品牌子")
    private String materialOlisBrandName;
}
