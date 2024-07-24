package com.fit.entity;

import com.fit.base.BaseEntity;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/07/17
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class SysMenu extends BaseEntity<SysMenu> {
    private static final long serialVersionUID = 1L;

    /** 父ID  (默认值为: 0) */
    private Long pid;

    /** 名称 (无默认值) */
    private String name;

    /** 图标 (无默认值) */
    private String icon;

    /** 类型,1:url 2:method  (默认值为: 0) */
    private Integer mold;

    /** 链接 (无默认值) */
    private String url;

    /** 优先权 (无默认值) */
    private Integer sort;

    /** 描述 (无默认值) */
    private String notes;

    /** 层级  (默认值为: 0) */
    private Integer level;

    /** 是否是超级权限 0非1是  (默认值为: 0) */
    private Integer isys;
}