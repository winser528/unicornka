package com.fit.entity;

import com.fit.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/07/26
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class SysJob extends BaseEntity<SysJob> {
    private static final long serialVersionUID = 1L;

    /** 任务名称 (无默认值) */
    private String jobName;

    /** cron表达式 (无默认值) */
    private String cronExpression;

    /** 任务执行类（包名+类名） (无默认值) */
    private String beanClass;

    /** 任务状态 (无默认值) */
    private String status;

    /** 任务分组 (无默认值) */
    private String jobGroup;

    /** 参数 (无默认值) */
    private String jobDataMap;

    /** 描述 (无默认值) */
    private String remarks;
}