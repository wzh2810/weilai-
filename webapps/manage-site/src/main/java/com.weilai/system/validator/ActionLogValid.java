package com.weilai.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/10/19
 */
@Data
public class ActionLogValid implements Serializable {
    @NotEmpty(message = "标题不能为空")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}