package com.fit.base;

import lombok.Data;

/**
 * @Author AIM
 * @Des 返回信息对象
 * @DATE 2018/1/31
 */
@Data
public class R {

    /**
     * 返回状态码
     */
    private int code;
    /**
     * 返回内容
     */
    private Object data;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据总数
     */
    private int total = -1;

    public R(int code, Object data, String msg, int total) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.total = total;
    }

    public static R getInstance(int code, Object data, String msg, int total) {
        return new R(code, data, msg, total);
    }

    public static R success(String msg, Object data) {
        return getInstance(0, data, msg, -1);
    }

    public static R success(String msg) {
        return success(msg, null);
    }

    public static R success() {
        return success("操作成功");
    }

    public static R error(String msg) {
        return getInstance(-1, null, msg, -1);
    }

    public static R error() {
        return error("操作失败");
    }

    /**
     * 返回列表信息
     *
     * @param total 页码总数
     * @param rows  返回数据
     */
    public static R tables(int total, Object rows) {
        return getInstance(0, rows, "查询成功", total);
    }

    public static R tree(int code, String msg, Object rows) {
        return getInstance(code, rows, msg, -1);
    }
}
