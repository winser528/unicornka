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
 * @DATE 2024/07/24
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class Pays extends BaseEntity<Pays> {
    private static final long serialVersionUID = 1L;

    /** 支付名称 (无默认值) */
    private String payName;

    /** 支付标识 (无默认值) */
    private String payCheck;

    /** 支付场景：1电脑pc 2手机 3全部  (默认值为: 1) */
    private Integer payClient;

    /** 支付方式 1跳转 2扫码 (无默认值) */
    private Integer payMethod;

    /** 商户账户 (无默认值) */
    private String merchantAccount;

    /** 商户 ID (无默认值) */
    private String merchantId;

    /** 商户公钥 (无默认值) */
    private String merchantKey;

    /** 商户私钥 (无默认值) */
    private String merchantPem;

    /** 支付回调通知路由 (无默认值) */
    private String notifyUrl;

    /** 同步通知路由 (无默认值) */
    private String returnUrl;

    /** 是否启用 1是 0否  (默认值为: 1) */
    private Boolean isOpen;

    /**  删除时间 (无默认值) */
    private Date deleteTime;
}