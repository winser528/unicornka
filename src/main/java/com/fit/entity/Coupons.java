package com.fit.entity;

import com.fit.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
public class Coupons extends BaseEntity<Coupons> {
    private static final long serialVersionUID = 1L;

    /** 优惠金额  (默认值为: 0.00) */
    private BigDecimal discount;

    /** 是否已经使用 1未使用 2已使用  (默认值为: 1) */
    private Boolean isUse;

    /** 是否启用 1是 0否  (默认值为: 1) */
    private Boolean isOpen;

    /** 优惠码 (无默认值) */
    private String coupon;

    /** 剩余使用次数  (默认值为: 0) */
    private Integer ret;

    /**  (无默认值) */
    private Date deleteTime;
}