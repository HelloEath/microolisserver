package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/22  16:41
 */
@Data
@Entity
@SQLDelete(sql = "update t_app_page_config set del = 1 where id = ?")
@Where(clause = "del = 0")
public class AppPageConfig extends BaseEntity {

    @Column(length = 255)
    @ApiModelProperty(value = "油品牌子")
    private String olisBrand;

    @Column(length = 255)
    @ApiModelProperty(value = "油品类型")
    private String olisType;

    @Column(length = 255)
    @ApiModelProperty(value = "图片页面标志")
    private String imgPageFlag;

    @ApiModelProperty(value = "图片地址")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String imgUrl;

    @Column(length = 255)
    @ApiModelProperty(value = "图片名字")
    private String imgName;

}
