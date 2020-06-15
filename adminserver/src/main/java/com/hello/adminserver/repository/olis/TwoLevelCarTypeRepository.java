package com.hello.adminserver.repository.olis;

import com.hello.common.dto.olis.TwoLevelCarType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/28  12:58
 */
public interface TwoLevelCarTypeRepository extends CrudRepository<TwoLevelCarType,Long>, JpaSpecificationExecutor<TwoLevelCarType> {
    List<TwoLevelCarType> findAllByOneId(Long id);
}
