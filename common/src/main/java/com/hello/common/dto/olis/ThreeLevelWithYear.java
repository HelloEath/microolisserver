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
 * @date 2020/2/4  10:33
 */
@Entity
@Data
@SQLDelete(sql = "update t_three_level_with_year set del = 1 where id = ?")
@Where(clause = "del = 0")
public class ThreeLevelWithYear extends BaseEntity {

    @ApiModelProperty(value = "三级车型")
    @ManyToOne
    private ThreeLevelCarType three;

    @ApiModelProperty(value = "年限")
    @ManyToOne
    private Year year;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;

    @ApiModelProperty(value = "发动机")
    @ManyToOne
    private Engine engine;

    @ApiModelProperty(value = "发动机")
    @ManyToOne
    private EngineType engineType;
}
