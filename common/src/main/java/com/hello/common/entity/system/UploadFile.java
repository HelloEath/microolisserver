package com.hello.common.entity.system;

import com.hello.common.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 上传的文件（图片、文件、视频等）
 * Created by hzh on 2018/6/21.
 */
@Entity
@ApiModel(value = "上传的文件UploadFile")
@SQLDelete(sql = "update t_upload_file set del = 1 where id = ?")
@Where(clause = "del = 0")
@Data
public class UploadFile extends BaseEntity {

    /**
     * 文件唯一编码
     */
    private String fileCode;

    /**
     * 文件名,part-uuid.originFileNmae
     */
    @Column(length = 400)
    private String fileName;

    /**
     * 原始文件名
     */
    private String originFileName;

    /**
     * 相对路径,基于app.baseUploadFilePath
     */
    private String path;


}
