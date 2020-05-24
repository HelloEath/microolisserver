package com.hello.common.dto.olis;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/10/27  18:57
 */
@Entity
@Data
@SQLDelete(sql = "update t_year set del = 1 where id = ?")
@Where(clause = "del = 0")
public class Year   extends BaseEntity {
    @Column(length = 255)
    @ApiModelProperty(value = "开始年限")
    private String startYear;

    @Column(length = 255)
    @ApiModelProperty(value = "结束年限")
    private String endYear;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;

    @Column(length = 255)
    @ApiModelProperty(value = "关键字")
    private String keyYearWords;

    @Transient
    List<EngineType> engineTypeList;

}
