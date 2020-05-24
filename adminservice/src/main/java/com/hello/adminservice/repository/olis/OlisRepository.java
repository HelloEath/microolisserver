package com.hello.adminservice.repository.olis;

import com.hello.common.dto.olis.Olis;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/11/10  14:54
 */
public interface OlisRepository extends CrudRepository<Olis,Long>, JpaSpecificationExecutor<Olis> {

    @Modifying
    @Query(value = "update t_img_material set material_prize=?2  where id =(select img_material_id from t_img_base where id=?1) and del=0 ",nativeQuery = true)
    void saveById(Long id, String olisPrize);
}
