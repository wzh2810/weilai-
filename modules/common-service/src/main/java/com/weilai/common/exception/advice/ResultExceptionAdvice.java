package com.weilai.common.exception.advice;


import com.weilai.common.utils.SpringContextUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常通知器
 * @author wangzhen
 * @date 2019/4/5
 */
@ControllerAdvice
public class ResultExceptionAdvice {
    /** 运行切入程序集合 */
    private List<ExceptionAdvice> proceed = new ArrayList<>();

    /** 添加切入程序 */
    public void putProceed(ExceptionAdvice advice){
        proceed.add(advice);
    }

    /** 执行异常通知 */
    public void runtimeException(RuntimeException e){
        for (ExceptionAdvice ea : proceed) {
            ExceptionAdvice advice = SpringContextUtil.getBean(ea.getClass());
            advice.run(e);
        }
    }
}
