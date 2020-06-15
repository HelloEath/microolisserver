package com.hello.adminserver.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时任务服务类，需要定时任务支持的都在此调用
 * Created by hzh on 2018/7/3.
 */
@Service
public class SchedulerService {
    @Scheduled(fixedRate=5000)
    public void task(){
        //每5秒钟执行一次
        //todo
    }
}
