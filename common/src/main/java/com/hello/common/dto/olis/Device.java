package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/22  16:23
 */
@Data
@Entity
@SQLDelete(sql = "update t_device set del = 1 where id = ?")
@Where(clause = "del = 0")
public class Device extends BaseEntity implements Serializable {

    @Column(length = 255)
    @ApiModelProperty(value = "设备唯一标识符")
    private String deviceCode;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;


    @ManyToOne
    @ApiModelProperty(value = "地区")
    private Region region;

    @Column(length = 2)
    @ApiModelProperty(value = "设备类型：电脑 01 |平板 02")
    private String deviceType;

    @ApiModelProperty(value = "每天剩余查询次数")
    private Integer deviceSearchCount=30;

    @Column(length = 255)
    @ApiModelProperty(value = "现居住地")
    private String deviceNowRegion;

    @Column(length = 255)
    @ApiModelProperty(value = "代理人")
    private String deviceProxy;

    @Column(length = 11)
    @ApiModelProperty(value = "联系电话")
    private String deviceProxyNumber;

    @Column(length = 1,columnDefinition = "int default 1")
    @ApiModelProperty(value = "授权状态,0已授权 1未授权")
    private int devicePermission=1;
}
