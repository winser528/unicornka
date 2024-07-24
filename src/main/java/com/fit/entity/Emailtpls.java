package com.fit.entity;

import com.fit.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/07/18
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class Emailtpls extends BaseEntity<Emailtpls> {
    private static final long serialVersionUID = 1L;

    /** 邮件标题 (无默认值) */
    private String tplName;

    /** 邮件内容 (无默认值) */
    private String tplContent;

    /** 邮件标识 (无默认值) */
    private String tplToken;

    /**  (无默认值) */
    private Date deleteTime;
}