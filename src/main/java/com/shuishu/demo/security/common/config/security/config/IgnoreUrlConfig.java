package com.shuishu.demo.security.common.config.security.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Objects;

/**
 * @author ：谁书-ss
 * @date ：2023-03-01 9:11
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：SpringSecurity 过滤的 URL
 */
@Configuration
@ConfigurationProperties(prefix = "ignore")
public class IgnoreUrlConfig {
    private List<String> urls;

    public boolean existsInIgnoreUrlList(String requestUrl) {
        if (Objects.isNull(urls) || urls.isEmpty()) {
            return false;
        }
        for (String url : urls) {
            boolean match = new AntPathMatcher().match(url, requestUrl);
            if (match) {
                return true;
            }
        }
        return false;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
