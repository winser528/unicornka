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
 * @DATE 2024/07/25
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class Orders extends BaseEntity<Orders> {
    private static final long serialVersionUID = 1L;

    /** 订单号 (无默认值) */
    private String orderSn;

    /** 关联商品id (无默认值) */
    private Long goodsId;

    /** 关联优惠码id  (默认值为: 0) */
    private Long couponId;

    /** 订单名称 (无默认值) */
    private String title;

    /** 订单类型: 1-自动发货 2-人工处理  (默认值为: 1) */
    private Boolean type;

    /** 商品单价  (默认值为: 0.00) */
    private BigDecimal goodsPrice;

    /** 购买数量  (默认值为: 1) */
    private BigDecimal buyAmount;

    /** 优惠码优惠价格  (默认值为: 0.00) */
    private BigDecimal couponDiscountPrice;

    /** 批发价优惠  (默认值为: 0.00) */
    private BigDecimal wholesaleDiscountPrice;

    /** 订单总价  (默认值为: 0.00) */
    private BigDecimal totalPrice;

    /** 实际支付价格  (默认值为: 0.00) */
    private BigDecimal actualPrice;

    /** 查询密码  (默认值为: ) */
    private String searchPwd;

    /** 下单邮箱 (无默认值) */
    private String email;

    /** 订单详情 (无默认值) */
    private String info;

    /** 支付通道id (无默认值) */
    private Long payId;

    /** 购买者下单IP地址 (无默认值) */
    private String buyIp;

    /** 第三方支付订单号  (默认值为: ) */
    private String tradeNo;

    /** 1待支付 2待处理 3处理中 4已完成 5处理失败 6异常 -1过期  (默认值为: 1) */
    private Integer status;

    /** 优惠码使用次数是否已经回退 0否 1是  (默认值为: 0) */
    private Boolean couponRetBack;

    /**  (无默认值) */
    private Date deleteTime;
}