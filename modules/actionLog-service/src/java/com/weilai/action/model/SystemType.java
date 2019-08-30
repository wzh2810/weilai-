package com.weilai.action.model;


import com.weilai.enums.ActionLogEnum;
import lombok.Getter;

/**
 * @author 小懒虫
 * @date 2018/10/15
 */
@Getter
public class SystemType extends BusinessType{

    /** 日志类型 */
    protected Byte type = ActionLogEnum.SYSTEM.getCode();

    public SystemType(String message) {
        super(message);
    }

    public SystemType(String name, String message) {
        super(name, message);
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
