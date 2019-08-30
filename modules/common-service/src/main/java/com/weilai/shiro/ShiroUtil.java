package com.weilai.shiro;


import com.weilai.common.utils.EncryptUtil;
import com.weilai.common.utils.SpringContextUtil;
import com.weilai.domain.Role;
import com.weilai.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RememberMeManager;

import java.util.Set;

/**
 * Shiro工具类
 * @author 小懒虫
 * @date 2018/8/14
 */
public class ShiroUtil {

    /**
     * 加密算法
     */
    public final static String HASH_ALGORITHM_NAME = EncryptUtil.HASH_ALGORITHM_NAME;

    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = EncryptUtil.HASH_ITERATIONS;

    /**
     * 加密处理
     * 备注：采用自定义的密码加密方式，其原理与SimpleHash一致，
     * 为的是在多个模块间可以使用同一套加密方式，方便共用系统用户。
     * @param password 密码
     * @param salt 密码盐
     */
    public static String encrypt(String password, String salt) {
        return EncryptUtil.encrypt(password, salt, HASH_ALGORITHM_NAME, HASH_ITERATIONS);
    }

    /**
     * 获取随机盐值
     */
    public static String getRandomSalt(){
        return EncryptUtil.getRandomSalt();
    }

    /**
     * 获取ShiroUser对象
     */
    public static User getSubject(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户IP地址
     */
    public static String getIp(){
        return SecurityUtils.getSubject().getSession().getHost();
    }

    /**
     * 重置Cookie“记住我”序列化信息
     */
    public static void resetCookieRememberMe(){
        Set<Role> roles = getSubject().getRoles();
        getSubject().setRoles(null);
        RememberMeManager meManager = SpringContextUtil.getBean(RememberMeManager.class);
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setRememberMe(true);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        info.setPrincipals(SecurityUtils.getSubject().getPrincipals());
        meManager.onSuccessfulLogin(SecurityUtils.getSubject(), token, info);
        getSubject().setRoles(roles);
    }
}
