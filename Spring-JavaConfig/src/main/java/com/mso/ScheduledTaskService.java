package com.mso;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ScheduledTaskService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:m:ss");
    @Scheduled(fixedRate = 5000)
    public void reportCurrenTime() {
        System.out.println("每隔5秒执行一次：" + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 10 13 ? * *")
    public void fixTimeExecution() {
        System.out.println("在指定时间："+dateFormat.format(new Date()) + "执行！");
    }
}
