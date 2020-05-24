package com.hello.adminservice.repository.saeDesc;

import com.hello.common.dto.olis.SaeDesc;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/4  10:59
 */
public interface SaeDescRepository extends CrudRepository<SaeDesc,Long>, JpaSpecificationExecutor<SaeDesc> {
    List<SaeDesc> findAllByEngineTypeId(Long engineTypeId);

    @Modifying
    @Query(value = "update SaeDesc set imgMaterialId=?1 where engineTypeId=?2 and del=0 ")
    void updateByEngineTypeId(Long imgMaterialId, Long EngineTypeId);

    List<SaeDesc> findAllByEngineTypeIdAndThreeId(Long id, Long id1);

    void deleteByEngineTypeIdAndThreeId(Long id, Long id1);

    List<SaeDesc> findAllByEngineId(Long id);

    void deleteByEngineId(Long id);
}
