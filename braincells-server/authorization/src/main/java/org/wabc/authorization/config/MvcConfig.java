package org.wabc.commons.authorization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置登录页面的视图信息
 * <a href="https://github.com/torlesse-liang/torlesse-oauth2">参考</a>
 * @author wabc
 * @version 1.0
 * @since 2024-04-01
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login").setViewName("login");
    }
}
