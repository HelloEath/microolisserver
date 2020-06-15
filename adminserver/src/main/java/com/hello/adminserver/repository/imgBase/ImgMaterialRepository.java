package com.hello.adminserver.repository.imgBase;

import com.hello.common.dto.olis.ImgMaterial;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/2  13:08
 */

public interface ImgMaterialRepository extends CrudRepository<ImgMaterial,Long>, JpaSpecificationExecutor<ImgMaterial> {
    ImgMaterial findByMaterialName(String engineName);
}
