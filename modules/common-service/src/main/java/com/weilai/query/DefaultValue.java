package com.weilai.query;

/**
 * 默认数据（在QueryType中，当属性值为DefaultValue时不放入查询条件中。）
 *
 * @author meyer [mamying@live.com]
 * @date 2015年8月25日 下午4:16:42
 * @Version 1.0
 */
public enum DefaultValue {
    /**
     * 为null
     */
    NULL,
    /**
     * 为null 或者 空串
     */
    EMPTY,
    /**
     * 值为-1(包含null 和 空串)
     */
    NEGATIVE,
    /**
     * 值为0(包含null 和 空串)
     */
    ZERO;

    /**
     * 判断是否为默认值，true为默认值，false为非默认
     *
     * @param value        需要判断的对象
     * @param defaultValue 默认值类型
     * @return
     */
    public static boolean isDefault(Object value, DefaultValue defaultValue) {
        boolean bool = false;
        switch (defaultValue) {
            case NULL:
                bool = value == null;
                break;
            case EMPTY:
                bool = (value == null || value.toString().isEmpty());
                break;
            case NEGATIVE:
                bool = (value == null || value.toString().isEmpty() || (Number.class
                        .isAssignableFrom(value.getClass()) ? (Number.class.cast(
                        value).intValue() == -1) : false));
                break;
            case ZERO:
                bool = (value == null || value.toString().isEmpty() || (Number.class
                        .isAssignableFrom(value.getClass()) ? (Number.class.cast(
                        value).intValue() == 0) : false));
                break;
            default:
                break;
        }
        return bool;
    }
}