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
public class Carmis extends BaseEntity<Carmis> {
    private static final long serialVersionUID = 1L;

    /** 所属商品 (无默认值) */
    private Long goodsId;

    /** 商品名称 (无默认值) */
    private String gdName;

    /** 状态 0未售出 1已售出  (默认值为: 0) */
    private Boolean status;

    /** 循环卡密 1是 0否  (默认值为: 0) */
    private Boolean isLoop;

    /** 卡密 (无默认值) */
    private String carmi;

    /**  (无默认值) */
    private Date deleteTime;
}