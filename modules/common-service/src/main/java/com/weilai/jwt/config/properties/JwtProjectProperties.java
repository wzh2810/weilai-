package com.weilai.jwt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt配置项
 * @author 小懒虫
 * @date 2019/4/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "project.jwt")
public class JwtProjectProperties {

    /** jwt秘钥 */
    private String secret = "mySecret";

    /** 过期时间(天)，默认3天  */
    private Integer expired = 3;

    /** 权限模式-路径拦截 */
    private boolean patternPath = false;

    /** 权限模式-注解拦截 */
    private boolean patternAnno = true;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public boolean isPatternPath() {
        return patternPath;
    }

    public void setPatternPath(boolean patternPath) {
        this.patternPath = patternPath;
    }

    public boolean isPatternAnno() {
        return patternAnno;
    }

    public void setPatternAnno(boolean patternAnno) {
        this.patternAnno = patternAnno;
    }
}
