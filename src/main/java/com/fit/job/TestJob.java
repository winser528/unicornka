package com.fit.job;

import java.time.LocalDateTime;

import com.fit.service.SysJobService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/7/26
 */
@Slf4j
public class TestJob implements Job {

    @Autowired
    private SysJobService jobService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        log.info("定时任务1 => 定时任务定时任务数量 => {}，参数值 => {}，当前时间 => {}", jobService.count(),
                "{ username=" + jobDataMap.get("username") + ", age=" + jobDataMap.get("age") + " }",
                LocalDateTime.now());
    }

}