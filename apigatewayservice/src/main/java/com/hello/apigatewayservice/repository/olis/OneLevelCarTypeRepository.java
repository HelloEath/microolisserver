package com.hello.apigatewayservice.repository.olis;

import com.hello.common.dto.olis.OneLevelCarType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/28  12:57
 */
public interface OneLevelCarTypeRepository extends CrudRepository<OneLevelCarType,Long>, JpaSpecificationExecutor<OneLevelCarType> {
    List<OneLevelCarType> findAllByBrandId(Long id);
}
