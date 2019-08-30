package com.weilai.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询类型注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryType {

    /**
     * 查询类型值
     *
     * @return
     */
    Restrictions value() default Restrictions.EQ;

    /**
     * 多个条件语句链接词 and 或者 or
     *
     * @return
     */
    Restrictions link() default Restrictions.AND;

    /**
     * or关系时，关系字段
     *
     * @return
     */
    String code() default "";

    /**
     * 字段
     *
     * @return
     */
    String column();

    /**
     * 当字段等于defaultValue时，不加入查询条件
     *
     * @return
     */
    DefaultValue defaultValue() default DefaultValue.EMPTY;

    /**
     * like匹配
     *
     * @return
     */
    MatchMode matchMode() default MatchMode.ANYWHERE;
}
