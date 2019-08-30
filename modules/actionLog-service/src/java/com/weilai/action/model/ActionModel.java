package com.weilai.action.model;

import lombok.Getter;

/**
 * @author 小懒虫
 * @date 2018/10/15
 */
@Getter
public class ActionModel {
    /** 日志名称 */
    protected String name;

    /** 日志类型 */
    protected Byte type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
