package com.hello.adminservice.repository.olis;

import com.hello.common.dto.olis.ThreeLevelCarType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/28  12:59
 */
public interface ThreeLevelCarTypeRepository extends CrudRepository<ThreeLevelCarType,Long>, JpaSpecificationExecutor<ThreeLevelCarType> {
    List<ThreeLevelCarType> findAllByTwoId(Long id);
}
