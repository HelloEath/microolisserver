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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/22  15:45
 */
@Entity
@Data
@SQLDelete(sql = "update t_two_level_car_type set del = 1 where id = ?")
@Where(clause = "del = 0")
public class TwoLevelCarType extends BaseEntity {

    @Column(length = 255)
    @ApiModelProperty(value = "名称")
    private String carTypeName;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;


    @ApiModelProperty(value = "一级车型")
    @ManyToOne
    private OneLevelCarType one ;

    /*@OneToMany(cascade={CascadeType.ALL},mappedBy="two",orphanRemoval = true,fetch = FetchType.EAGER)
    List<ThreeLevelCarType> threeLevelCarTypeList=new ArrayList<>();*/

    @Transient
    List<ThreeLevelCarType> threeLevelCarTypeList=new ArrayList<>();

    @Override
    public String toString() {
        return "TwoLevelCarType{" +
                "carTypeName='" + carTypeName + '\'' +
                ", one=" + one +
                '}';
    }
}
