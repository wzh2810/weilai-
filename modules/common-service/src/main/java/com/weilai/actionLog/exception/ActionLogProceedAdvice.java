package com.weilai.actionLog.exception;


import com.weilai.actionLog.action.SystemAction;
import com.weilai.actionLog.annotation.ActionLog;
import com.weilai.common.exception.advice.ExceptionAdvice;

/**
 * 运行时抛出的异常进行日志记录
 * @author 小懒虫
 * @date 2019/4/6
 */
public class ActionLogProceedAdvice implements ExceptionAdvice {

    @Override
    @ActionLog(key = SystemAction.RUNTIME_EXCEPTION, action = SystemAction.class)
    public void run(RuntimeException e) {}
}
