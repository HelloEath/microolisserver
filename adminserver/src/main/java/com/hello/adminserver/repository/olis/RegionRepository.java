package com.hello.adminserver.repository.olis;

import com.hello.common.dto.olis.Region;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/28  16:43
 */
public interface RegionRepository extends CrudRepository<Region,Long>, JpaSpecificationExecutor<Region> {
    @Query(value = "select count(*) from t_region t where t.del = 0  and t.region_name=?1  ", nativeQuery = true)
    int findByRegionName(String regionName);


    @Override
    List<Region> findAll();
    List<Region> findAllByRegionName(String regionName);

    @Query(value = "select count(*) from t_region t where t.del = 0  and t.region_name=?1 and t.system_type=?2 and t.del=0 ", nativeQuery = true)
    int findByRegionNameAndSystemType(String regionName, String systemType);

    List<Region> findAllBySystemType(String systemType);

    List<Region> findAllByRegionNameAndSystemType(String regionName, String systemType);
}
