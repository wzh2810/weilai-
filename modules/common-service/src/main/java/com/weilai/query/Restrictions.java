package com.weilai.query;

public enum Restrictions {
    /**
     * equal,等于.
     */
    EQ,
    /**
     * 不等于 <>
     */
    NEQ,
    /**
     * great-than > 大于
     */
    GT,
    /**
     * great-equal >= 大于等于
     */
    GE,
    /**
     * less-than, < 小于
     */
    LT,
    /**
     * less-equal <= 小于等于
     */
    LE,
    /**
     * 对应SQL的LIKE子句
     */
    LIKE,
    /**
     * 对应SQL的in子句
     */
    IN,
    /**
     * 对应SQL的not in子句
     */
    NOTIN,
    /**
     * 判断属性是否为空,为空则返回true
     */
    ISNULL,
    /**
     * 与isNull相反
     */
    ISNOTNULL,
    /**
     * 判断属性是否为空或空串，为空或空串则返回true
     */
    ISEMPTY,
    /**
     * 与isEmpty相反
     */
    ISNOTEMPTY,
    /**
     * and 关系
     */
    AND,
    /**
     * or 关系
     */
    OR,
    /**
     * 闭区间min,例如：min &le; column &le; max中的min
     */
    BETWEEN_NOT_EQ_MIN,
    /**
     * 闭区间max,例如：min &le; column &le; max中的max
     */
    BETWEEN_NOT_EQ_MAX,
    /**
     * 闭区间min,例如：min &ang; column &le; max中的min
     */
    BETWEEN_EQ_MIN,
    /**
     * 闭区间max,例如：min &ang; column &le; max中的max
     */
    BETWEEN_EQ_MAX,
    /**
     * 小于值min或者大于值max 例如：min &ge; column &ge; max中的min
     */
    NOTBETWEEN_NOT_EQ_MIN,
    /**
     * 小于值min或者大于值max 例如：min &ge; column &ge; max中的max
     */
    NOTBETWEEN_NOT_EQ_MAX,
    /**
     * 小于值min或者大于值max 例如：min &ge; column &ge; max中的min
     */
    NOTBETWEEN_EQ_MIN,
    /**
     * 小于值min或者大于值max 例如：min &ge; column &ge; max中的max
     */
    NOTBETWEEN_EQ_MAX;
}
