package com.hello.apigatewayservice.repository.engine;

import com.hello.common.dto.olis.EngineType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/31  10:52
 */
public interface EngineTypeRepository extends CrudRepository<EngineType,Long>, JpaSpecificationExecutor<EngineType> {
    List<EngineType> findAllByYearId(Long id);

    EngineType findByYearIdAndSystemTypeAndEngineTypeName(Long id, String systemType, String engineTypeName);

    List<EngineType> findAllByEngineTypeNameAndSystemTypeAndYearId(String engineTypeName, String systemType, Long yearId);
}
