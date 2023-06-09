package com.lrt.syoa.config.Schedule;

import cn.hutool.core.io.FileUtil;
import com.lrt.syoa.mapper.PeoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * 定时任务
 */
@Component
public class SchedulingTaskBasic {

    @Autowired
    private PeoMapper peoMapper;
    @Value("${files.upload.path}")
    private String fileUploadPath;
    /**
     * 删除多余的文件
     * 每天3 12 19点
     */
    @Scheduled(cron = "0 0 3,12,19 * * *")
    private void clearImg() {
        Set<String> sqlImgs = peoMapper.selectAllImg();
        List<File> dirImgs = FileUtil.loopFiles(fileUploadPath);
        for (File dirImg: dirImgs) {
            // 如果数据库中不存在此文件，则删除
            if (!sqlImgs.contains(dirImg.getName())) {
                dirImg.delete();
            }
        }
    }

}
