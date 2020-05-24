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
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/10/27  18:53
 */
@Entity
@Data
@SQLDelete(sql = "update t_brand set del = 1 where id = ?")
@Where(clause = "del = 0")
public class Brand extends BaseEntity {
    @Column(length = 255)
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "系统类型")
    @Column(length = 100)
    private String systemType;

    @Column(length = 1)
    @ApiModelProperty(value = "品牌首字母")
    private String brandFistName;

    @Column(length = 255)
    @ApiModelProperty(value = "提示音")
    private String brandSound;

    @ApiModelProperty(value = "logo地址")
    @ManyToOne
    private UploadFile uploadFile;

   /* @JsonIgnore
    @OneToMany(cascade={CascadeType.MERGE},mappedBy="brand",orphanRemoval = true,fetch = FetchType.EAGER)
    List<OneLevelCarType> oneLevelCarTypeList;*/

   @Transient
    List<OneLevelCarType> oneLevelCarTypeList;

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }
        if(obj == null){
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }
        if(obj instanceof Brand){
            Brand other = (Brand) obj;
            //需要比较的字段相等，则这两个对象相等
            if(this.getId().equals(other.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getId() == null ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandName='" + brandName + '\'' +
                ", brandFistName='" + brandFistName + '\'' +
                ", brandSound='" + brandSound + '\'' +
                ", uploadFile=" + uploadFile +
                '}';
    }

    public static void main(String[] args) {

        Long l1=new Long(33L);
        Long l2=new Long(33L);
        System.out.print(l1==l2);

    }
}
