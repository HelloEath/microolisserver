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
 * @date 2019/10/27  18:53
 */
@Data
@Entity
@SQLDelete(sql = "update t_region_prize_manage set del = 1 where id = ?")
@Where(clause = "del = 0")
public class RegionPrizeManage extends BaseEntity {
    @ManyToOne
    @ApiModelProperty(value = "油品")
    private ImgBase imgBase;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;

    @ManyToOne
    @ApiModelProperty(value = "地区")
    private Region region;

    @Column(length = 255)
    @ApiModelProperty(value = "价格")
    private String olisPrize;

}
