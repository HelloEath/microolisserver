package com.hello.adminservice.repository.imgBase;

import com.hello.common.dto.olis.ImgBase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/2  12:27
 */
public interface ImgBaseRepository extends CrudRepository<ImgBase,Long>, JpaSpecificationExecutor<ImgBase> {
    @Query(value = "select * from t_img_base  t1 join t_img_material t2 on t1.img_material_id=t2.id  and t2.material_Type=?1 and t1.system_type=?2 and t1.del=0 ",nativeQuery = true)
    List<ImgBase> findAllByWhere(Integer materialType, String systemType);

    List<ImgBase> findAllBySystemType(String systemType);
}
