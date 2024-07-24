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
public class GoodsGroup extends BaseEntity<GoodsGroup> {
    private static final long serialVersionUID = 1L;

    /** 分类名称 (无默认值) */
    private String gpName;

    /** 是否启用，1是 0否  (默认值为: 1) */
    private Boolean isOpen;

    /** 排序权重 越大越靠前  (默认值为: 1) */
    private Integer ord;

    /**  (无默认值) */
    private Date deleteTime;
}