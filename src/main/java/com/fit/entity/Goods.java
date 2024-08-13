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
public class Goods extends BaseEntity<Goods> {
    private static final long serialVersionUID = 1L;

    /** 所属分类id (无默认值) */
    private Long groupId;

    /** 商品名称 (无默认值) */
    private String gdName;

    /** 商品描述 (无默认值) */
    private String gdDescription;

    /** 商品关键字 (无默认值) */
    private String gdKeywords;

    /** 商品图片 (无默认值) */
    private String picture;

    /** 零售价  (默认值为: 0.00) */
    private BigDecimal retailPrice;

    /** 实际售价  (默认值为: 0.00) */
    private BigDecimal actualPrice;

    /** 库存  (默认值为: 0) */
    private Integer inStock;

    /** 销量  (默认值为: 0) */
    private Integer salesVolume;

    /** 排序权重 越大越靠前  (默认值为: 1) */
    private Integer ord;

    /** 限制单次购买最大数量，0为不限制  (默认值为: 0) */
    private Integer buyLimitNum;

    /** 购买提示 (无默认值) */
    private String buyPrompt;

    /** 商品描述 (无默认值) */
    private String description;

    /** 商品类型  0人工处理 1自动发货  (默认值为: 1) */
    private Boolean type;

    /** 批发价配置 (无默认值) */
    private String wholesalePriceCnf;

    /** 其他输入框配置 (无默认值) */
    private String otherIpuCnf;

    /** 回调事件 (无默认值) */
    private String apiHook;

    /** 是否启用，1是 0否  (默认值为: 1) */
    private Boolean isOpen;

    /**  (无默认值) */
    private Date deleteTime;
}