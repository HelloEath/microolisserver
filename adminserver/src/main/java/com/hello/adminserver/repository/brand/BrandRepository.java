package com.hello.adminserver.repository.brand;

import com.hello.common.dto.olis.Brand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/23  10:25
 */
public interface BrandRepository extends CrudRepository<Brand,Long>, JpaSpecificationExecutor<Brand> {
    List<Brand> findAllByBrandName(String brandName);

    @Query(value = "select * from t_brand t1 join t_upload_file t2 on t1.upload_file_id=t2.id and t1.del=0 ",nativeQuery = true)
    List<Brand> Brands();

    List<Brand> findAllBySystemType(String systemType);

    List<Brand> findAllByBrandNameAndSystemType(String brandName, String systemType);
}
