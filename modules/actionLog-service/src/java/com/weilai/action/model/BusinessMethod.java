package com.weilai.action.model;


import com.weilai.enums.ActionLogEnum;
import lombok.Getter;

/**
 * @author 小懒虫
 * @date 2018/10/15
 */
@Getter
public class BusinessMethod extends ActionModel{
    /** 日志名称 */
    protected String name;

    /** 行为方法名 */
    protected String method;

    /** 日志类型 */
    protected Byte type = ActionLogEnum.BUSINESS.getCode();

    /**
     * 只构建行为方法名，日志名称由日志注解name定义
     * @param method 行为方法名
     */
    public BusinessMethod(String method) {
        this.method = method;
    }

    /**
     * 构建日志名称和行为方法名
     * @param name 日志名称
     * @param method 行为方法名
     */
    public BusinessMethod(String name, String method) {
        this.name = name;
        this.method = method;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public Byte getType() {
        return type;
    }

    @Override
    public void setType(Byte type) {
        this.type = type;
    }
}
