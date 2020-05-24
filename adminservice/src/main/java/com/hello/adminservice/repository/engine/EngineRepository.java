package com.hello.adminservice.repository.engine;

import com.hello.common.dto.olis.Engine;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/1  14:42
 */
public interface EngineRepository extends CrudRepository<Engine,Long>, JpaSpecificationExecutor<Engine> {

    Engine findByYearIdAndThreeIdAndEngineTypeId(Long id, Long id1, Long id2);
}
