package com.weilai.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目配置项
 * @author wangzhen
 * @date 2018/11/6
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {

    /** 是否开启验证码 */
    private boolean captchaOpen = false;

    /** xss防护设置 */
    private ProjectProperties.Xxs xxs = new ProjectProperties.Xxs();

    /**
     * xss防护设置
     */
    @Data
    public static class Xxs {
        // xss防护开关
        private boolean enabled = true;
        // 拦截规则，可通过“,”隔开多个
        private String urlPatterns = "/*";
        // 忽略规则，可通过“,”隔开多个
        private String excludes = "/favicon.ico,/img/*,/js/*,/css/*,/lib/*";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getUrlPatterns() {
            return urlPatterns;
        }

        public void setUrlPatterns(String urlPatterns) {
            this.urlPatterns = urlPatterns;
        }

        public String getExcludes() {
            return excludes;
        }

        public void setExcludes(String excludes) {
            this.excludes = excludes;
        }
    }

    public boolean isCaptchaOpen() {
        return captchaOpen;
    }

    public void setCaptchaOpen(boolean captchaOpen) {
        this.captchaOpen = captchaOpen;
    }

    public Xxs getXxs() {
        return xxs;
    }

    public void setXxs(Xxs xxs) {
        this.xxs = xxs;
    }
}
