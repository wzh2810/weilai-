package com.weilai.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
@Data
public class DeptValid implements Serializable {
	@NotEmpty(message = "部门名称不能为空")
	private String title;
    @NotNull(message = "父级部门不能为空")
    private Long pid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
