package com.lrt.syoa.config.Schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class SchedulingTaskBasic {

    /**
     * 没清除数据库中不存在的图片
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void clearImg() {

    }

}
