package com.hello.adminserver.repository;

import com.hello.common.entity.system.UploadFile;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by hzh on 2018/7/4.
 */
public interface UploadFileRepository extends CrudRepository<UploadFile,Long> {
    UploadFile findByFileCode(String code);
}
