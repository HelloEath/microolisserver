package com.hello.common.entity.common;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
//import java.time.LocalDateTime;

/**
 * 基类
 * Created by Hzh on 17/4/12.
 */

@MappedSuperclass
@Data
@ToString
public class BaseEntity  implements Serializable {

    @Id
    @GeneratedValue
//    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="sequence")
    //seq_biz_claim_voucher_detail为oracle序列名称allocationSize设置自动增长数
//    @SequenceGenerator(name="sequence",sequenceName="seq_biz_claim_voucher_detail",allocationSize=1)
    private Long id;
//    private LocalDateTime createTime;
    @Column(name = "create_time",columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time",columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;



    //save操作前触发该方法，用于更新updateTime
    @PreUpdate
    protected void preUpdate(){
        updateTime=new Date();
    }

    //delete前触发该放方法，用于更新updateTime
    @PreRemove
    protected void preRemove(){
        updateTime=new Date();
    }


    public BaseEntity() {
        //createTime = LocalDateTime.now();
        createTime = new Date();
    }

    @Column(length = 1, nullable = false, columnDefinition = "int default 0")
    private int del;


}
