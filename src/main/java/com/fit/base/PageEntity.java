package com.fit.base;

import java.io.Serializable;

/**
 * @Author AIM
 * @Des 分页基类
 * @DATE 2020/8/18
 */
public class PageEntity extends BaseEntity implements Serializable {

    private Integer pageNumber = 0;
    private Integer pageSize = 10;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
