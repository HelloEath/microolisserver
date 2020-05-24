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
 * @date 2020/1/22  15:58
 */
@Entity
@Data
@SQLDelete(sql = "update t_engine_type set del = 1 where id = ?")
@Where(clause = "del = 0")
public class EngineType extends BaseEntity {

    @Column(length = 255)
    @ApiModelProperty(value = "发动机类型名称")
    private String engineTypeName;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;


    @ManyToOne
    @ApiModelProperty(value = "年限")
    private Year year;

    @Transient
    ImgBase imgBase;

    @Transient
    List<SaeDesc> saeDescList;

}
