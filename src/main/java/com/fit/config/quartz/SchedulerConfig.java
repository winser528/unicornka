package com.fit.config.quartz;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @AUTO quartz配置
 * @Author AIM
 * @DATE 2024/7/26
 */
@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulerFactoryBeanCustomizer {

    @Resource
    private DataSource dataSource;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        // 启动延时
        schedulerFactoryBean.setStartupDelay(10);
        // 自动启动任务调度
        schedulerFactoryBean.setAutoStartup(true);
        // 是否覆盖现有作业定义
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        // 配置数据源
        schedulerFactoryBean.setDataSource(dataSource);
    }
}
