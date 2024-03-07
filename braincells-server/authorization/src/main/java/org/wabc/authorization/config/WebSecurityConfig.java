package org.wabc.authorization.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 启用基于Spring Security的Web安全功能；重写其中的方法来配置安全策略、认证方式、访问控制规则。
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-30
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            1.在 Spring Security 中，对 OAuth2 提供了一些默认的可访问端点（URL），用于处理 OAuth2 相关的认证和授权流程。以下是默认可访问端点：
                /oauth/authorize：用于进行用户认证和授权的端点。客户端应用可以将用户重定向到此端点，以获取用户的授权。
                /oauth/token：用于获取访问令牌（Access Token）的端点。客户端应用可以通过发送合适的请求参数来获取访问令牌，以便在后续的 API 请求中进行身份验证和授权。
                /oauth/check_token：用于验证访问令牌的有效性的端点。客户端应用可以使用该端点检查访问令牌是否有效。
                /oauth/token_key：用于获取用于 JWT 令牌签名验证的公钥的端点。如果使用 JWT 令牌，则客户端应用可以通过该端点获取公钥来验证令牌签名。
                /oauth/error：用于显示 OAuth2 相关错误信息的端点。当发生 OAuth2 认证或授权的错误时，客户端应用可能会被重定向到此端点来查看错误信息。

            2.前后端分离的应用,需要关闭csrf来避免跨域请求造成的问题。因为不同域名或主机来托管前端和后端代码，浏览器会自动首先执行
          预检（Preflight）-OPTIONS 请求到后端服务器，CSRF令牌没有来源，也无法手动添加，会导致请求被阻止。
            3.网上部分文章有配置requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()；是属于Spring Boot Actuator 端点.
          /actuator/..，这里不配置监控。

         */
        http.authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置用户密码加密器
        // AuthorizationServerConfig endpoints不支持passwordEncoder方法。只能在这里指定
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * oatuh2 授权类型为密码模式，authorizedGrantTypes=password时要使用此认证管理器。
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * UserDetails用户详细信息密码加密器。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
