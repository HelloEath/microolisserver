package com.hello.adminservice.repository;

import com.hello.common.dto.olis.ThreeLevelWithYear;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/4  16:43
 */
public interface ThreeLevelWithYearRepository extends CrudRepository<ThreeLevelWithYear,Long>, JpaSpecificationExecutor<ThreeLevelWithYear> {

    void deleteByThreeIdAndYearId(Long id, Long id1);

    ThreeLevelWithYear findByThreeIdAndYearId(Long id, Long id1);

    ThreeLevelWithYear findByEngineId(Long id);

    @Modifying
    @Query(value = "update t_Three_Level_With_Year set three_Id=?1 ,year_id=?2,engine_type_id=?3 where engine_id=?3 and del=0",nativeQuery = true)
    void updateByEngineId(Long id, Long id1, Long id3, Long id2);

    List<ThreeLevelWithYear> findByThreeId(Long id);

    List<ThreeLevelWithYear> findAllByYearIdAndThreeId(Long id, Long threeId);
}
