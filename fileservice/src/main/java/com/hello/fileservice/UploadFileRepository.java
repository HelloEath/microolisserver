package com.hello.fileservice;

import com.hello.common.entity.system.UploadFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hzh on 2018/7/4.
 */
public interface UploadFileRepository extends CrudRepository<UploadFile,Long> {
    UploadFile findByFileCode(String code);
}
