package com.panshi.starter.configurer;

import org.springframework.boot.context.properties.ConfigurationProperties;

/***
 * @Auther: guo
 * @Date: 21:37 2020/9/15
 */
@ConfigurationProperties(prefix = "superbeyone.hello")
public class HelloProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
