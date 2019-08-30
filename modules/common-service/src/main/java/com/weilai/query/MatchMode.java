package com.weilai.query;


public enum MatchMode {
    /**
     * 不进行like操作
     */
    NOT,
    /**
     * 字符串在中间匹配.相当于"like '%value%'"
     */
    ANYWHERE,
    /**
     * like前部 --> 字符串在最前面的位置.相当于"like 'value%'"
     */
    START,
    /**
     * like后部-->字符串在最后面的位置.相当于"like '%value'"
     */
    END,
    /**
     * 字符串精确匹配.相当于"like 'value'"
     */
    EXACT;

}
