package com.hello.apigatewayservice.repository.olis;

import com.hello.common.dto.olis.RegionPrizeManage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/29  20:16
 */
public interface RegionPrizeManageRepository extends CrudRepository<RegionPrizeManage,Long>, JpaSpecificationExecutor<RegionPrizeManage> {
    List<RegionPrizeManage> findAllByRegionId(Long id);

    void deleteByImgBaseId(Long id);

    RegionPrizeManage findByRegionIdAndImgBaseId(Long id, Long id1);
}
