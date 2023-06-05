package org.worker.itagent1.auth.oauth.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName WebSecurityConfig
 * @description: 安全拦截配置
 * @date 2023/5/22 22:39
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Security接口拦截配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/oauth/**").permitAll()
                // @link https://gitee.com/xiaoym/knife4j/issues/I1Q5X6 (接口文档knife4j需要放行的规则)
                .antMatchers("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

