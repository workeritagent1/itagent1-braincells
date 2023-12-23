package org.wabc.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName AuthorizationServerConfig
 * @description:
 * @date 2023/12/19 23:11
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //  基于 JDBC 实现，需要事先在数据库配置客户端信息
        clients.jdbc(dataSource);
        /*
        clients.inMemory()
                // 客户端id
                .withClient("client_id")
                //客户端密码
                .secret(passwordEncoder().encode("secret"))
                //授权类型
                .authorizedGrantTypes("password","authorization_code","client_credentials","refresh_token","implicit")
                .scopes("all") //  .scopes("read", "write")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(60 * 60 * 12)
                // 0表示不过期
                .refreshTokenValiditySeconds(0)
                // 重定向地址,返回code使用
                .redirectUris("http://baidu.com");
         */
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                // Spring Security OAuth2 默认提供的 JwtTokenStore 将 JWT 令牌的生成、解析和验证集成在一起。
                // TokenStore 会被自动使用，而不需要显式配置。
                .accessTokenConverter(jwtAccessTokenConverter());


    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        // 文件存储密码："Wabc@2023"
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt_keystore.jks"), "Wabc@2023".toCharArray());
        // 私钥密码
        KeyPair keyPair = factory.getKeyPair("jwt", "Wabc@2023".toCharArray());
        return keyPair;
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
