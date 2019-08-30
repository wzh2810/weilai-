package com.weilai.actionLog.action;



import com.weilai.actionLog.action.base.BaseActionMap;
import com.weilai.actionLog.action.base.ResetLog;
import com.weilai.common.enums.StatusEnum;
import com.weilai.common.utils.StatusUtil;

import java.util.List;

/**
 * 通用：记录数据状态的行为
 *
 * @author 小懒虫
 * @date 2018/10/14
 */
public class StatusAction extends BaseActionMap {

    @Override
    public void init() {
        // 记录数据状态改变日志
        putMethod("default", "defaultMethod");
    }

    /**
     * 重新包装保存的数据行为方法
     *
     * @param resetLog ResetLog对象数据
     */
    @SuppressWarnings("unchecked")
    public static void defaultMethod(ResetLog resetLog) {
        if(resetLog.isSuccessRecord()){
            String param = (String) resetLog.getParam("param");
            StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
            List<Long> ids = (List<Long>) resetLog.getParam("ids");
            resetLog.getActionLog().setMessage(statusEnum.getMessage() + "ID：" + ids.toString());
        }
    }
}
