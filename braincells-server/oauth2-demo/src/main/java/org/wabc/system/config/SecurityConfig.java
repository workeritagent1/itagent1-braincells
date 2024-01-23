package org.wabc.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName SecurityConfig
 * @description:
 * @date 2023/12/25 22:56
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 默认情况下不需要做任何修改，Spring Security 将启用基于表单的登录和默认的用户认证机制。
}
