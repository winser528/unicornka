package com.fit.config.quartz;

public enum StatusEnum {

    NONE(0, "NONE", "停止"),
    NORMAL(1, "NORMAL", "正常"),
    PAUSED(2, "PAUSED", "暂停"),
    COMPLETE(3, "COMPLETE", "完成"),
    ERROR(4, "ERROR", "错误"),
    BLOCKED(5, "BLOCKED", "锁定");

    private Integer id;
    private String name;
    private String state;

    StatusEnum(String name, String state) {
        this.name = name;
        this.state = state;
    }

    StatusEnum(Integer id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
