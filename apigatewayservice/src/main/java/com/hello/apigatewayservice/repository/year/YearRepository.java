package com.hello.apigatewayservice.repository.year;

import com.hello.common.dto.olis.Year;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  20:53
 */
public interface YearRepository extends CrudRepository<Year,Long>, JpaSpecificationExecutor<Year> {
    List<Year> findAllBySystemType(String systemType);

    Year findBySystemTypeAndKeyYearWords(String systemType, String keyYearWords);

    List<Year> findAllById(Long id);

}
