package com.fit.config.quartz;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.fit.entity.SysJob;
import com.fit.service.SysJobService;
import com.fit.util.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/7/26
 */
@Slf4j
@Component
public class QuartzHandler {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SysJobService service;

    @EventListener
    private void onApplicationEvent(ContextRefreshedEvent event) {
        List<SysJob> jobs = service.findList(new SysJob());
        for (SysJob job : jobs) {
            // 检查作业是否已存在
            if (has(job)) {
                runStatus(job.getStatus(), job);
            } else {
                this.create(job);
            }
        }
    }

    public void runStatus(String status, SysJob job) {
        switch (status) {
            case "NORMAL":
                this.restart(job);
                break;
            case "PAUSED":
                this.pause(job);
                break;
        }
    }

    /**
     * 将任务参数转换成JobDataMap
     *
     * @param jobData
     * @return
     */
    private JobDataMap converter(String jobData) {
        // 处理参数
        JobDataMap map = new JobDataMap();
        if (isNotEmpty(jobData)) {
            if (isJson(jobData)) {
                map.putAll(FastJsonUtil.json2Map(jobData));
            }
        }
        return map;
    }

    /**
     * 新增定义任务
     *
     * @param job 定义任务
     * @return
     */
    public boolean create(SysJob job) {
        boolean result = true;
        try {
            String jobName = job.getJobName();
            String jobGroup = job.getJobGroup();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            if (null == cronTrigger) {
                // 启动定时任务
                JobDetail jobDetail = JobBuilder.newJob(this.getClazz(job.getBeanClass())).withIdentity(jobName, jobGroup)
                        .setJobData(this.converter(job.getJobDataMap())).build();
                // 设置启动周期
                cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(cronSchedule).build();
                scheduler.scheduleJob(jobDetail, cronTrigger);
                if (!(job.getStatus().equals(StatusEnum.NORMAL.getState()))) {
                    scheduler.pauseJob(jobDetail.getKey());
                }
            } else {
                // 重启定时任务
                cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronSchedule).build();
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
        } catch (SchedulerException | ClassNotFoundException e) {
            log.info("新增定时任务异常：{}", e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 暂停定时任务
     *
     * @param job 定时任务
     * @return
     */
    public boolean pause(SysJob job) {
        boolean result = true;
        try {
            JobKey jobKey = new JobKey(job.getJobName(), job.getJobGroup());
            scheduler.pauseJob(jobKey);
            log.info(String.format("暂停定时任务：%s", job.getJobName()));
        } catch (SchedulerException e) {
            log.error("暂停定时任务异常：{}", e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 重启定时任务
     *
     * @param job 定时任务
     * @return
     */
    public boolean restart(SysJob job) {
        boolean result = true;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            scheduler.rescheduleJob(triggerKey, trigger);
            log.info(String.format("重启定时任务：%s", job.getJobName()));
        } catch (SchedulerException e) {
            log.error("重启定时任务异常：{}", e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 立即执行一次
     *
     * @param job 定时任务
     * @return
     */
    public boolean trigger(SysJob job) {
        boolean result = true;
        try {
            JobKey jobKey = new JobKey(job.getJobName(), job.getJobGroup());
            scheduler.triggerJob(jobKey);
            log.info(String.format("立即执行定时任务：%s", job.getJobName()));
        } catch (SchedulerException e) {
            log.error("立即执行一次异常：{}", e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 修改触发时间表达式
     *
     * @param job 定时任务
     * @return
     */
    public boolean updateCronExpression(SysJob job) {
        boolean result = true;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            if (!(job.getStatus().equals(StatusEnum.NORMAL.getState()))) {
                scheduler.rescheduleJob(triggerKey, cronTrigger);
                log.info(String.format("立即执行定时任务：%s", job.getJobName()));
            }
        } catch (SchedulerException e) {
            log.error("修改触发时间表达式异常：{}", e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 删除定时任务
     *
     * @param job 定时任务
     * @return
     */
    public boolean delete(SysJob job) {
        boolean result = true;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobKey jobKey = trigger.getJobKey();
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
            log.info(String.format("删除定时任务：%s", job.getJobName()));
        } catch (SchedulerException e) {
            log.error("删除定时任务异常：{}", e.getMessage());
            result = false;
        }
        return result;
    }

    /***
     * 判断是否存在定时任务
     *
     * @param job 定时任务
     * @return
     */
    public boolean has(SysJob job) {
        boolean result = false;
        try {
            if (!scheduler.isShutdown()) {
                JobKey jobKey = new JobKey(job.getJobName(), job.getJobGroup());
                result = scheduler.checkExists(jobKey);
            }
        } catch (SchedulerException e) {
            log.info("判断是否存在定时任务异常：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 获得定时任务状态
     *
     * @param job 定时任务
     * @return
     */
    public String getStatus(SysJob job) {
        String status = "";
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            status = triggerState.toString();
        } catch (Exception e) {
            log.info("获得定时任务状态异常：{}", e.getMessage());
        }
        return isNotEmpty(status) ? status : TriggerState.NONE.toString();
    }

    /**
     * 启动调度器
     */
    public boolean startScheduler() {
        boolean result = false;
        try {
            scheduler.start();
            result = true;
        } catch (SchedulerException e) {
            log.info("启动调度器异常：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 关闭调度器
     */
    public boolean standbyScheduler() {
        boolean result = false;
        try {
            if (!scheduler.isShutdown()) {
                scheduler.standby();
                result = true;
            }
        } catch (SchedulerException e) {
            log.info("关闭调度器异常：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 判断调度器是否为开启状态
     */
    public boolean isStarted() {
        boolean result = false;
        try {
            result = scheduler.isStarted();
        } catch (SchedulerException e) {
            log.info("判断调度器是否为开启状态异常：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 判断调度器是否为关闭状态
     */
    public boolean isShutdown() {
        boolean result = true;
        try {
            result = scheduler.isShutdown();
        } catch (SchedulerException e) {
            log.info("判断调度器是否为关闭状态异常：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 判断调度器是否为待机状态
     */
    public boolean isInStandbyMode() {
        boolean result = true;
        try {
            result = scheduler.isInStandbyMode();
        } catch (SchedulerException e) {
            log.info("判断调度器是否为待机状态异常：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 获得下一次执行时间
     *
     * @param cronExpression cron表达式
     * @return
     */
    public LocalDateTime nextFireDate(String cronExpression) {
        LocalDateTime localDateTime = null;
        try {
            if (isNotEmpty(cronExpression)) {
                CronExpression ce = new CronExpression(cronExpression);
                Date nextInvalidTimeAfter = ce.getNextInvalidTimeAfter(new Date());
                localDateTime = Instant.ofEpochMilli(nextInvalidTimeAfter.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
        } catch (ParseException e) {
            log.info("获得下一次执行时间异常：{}", e.getMessage());
        }
        return localDateTime;
    }

    private static Class<? extends Job> getClazz(String forName) throws ClassNotFoundException {
        try {
            return Class.forName(forName).asSubclass(Job.class);
        } catch (Exception e) {
            log.error("获取任务的类失败", e);
            throw new ClassNotFoundException(String.format("类[%s]不存在", forName));
        }
    }

    private static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    private static boolean isJson(String inStr) {
        if (isNotEmpty(inStr)) {
            String str = inStr.trim();
            return (str.charAt(0) == "{".charAt(0) && str.charAt(str.length() - 1) == "}".charAt(0)) ||
                    (str.charAt(0) == "[".charAt(0) && str.charAt(str.length() - 1) == "]".charAt(0));
        }
        return false;
    }
}
