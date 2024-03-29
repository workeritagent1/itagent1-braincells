//package org.wabc.authorization.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
//
//import java.security.KeyPair;
//
///**
// * 授权服务器
// */
//@Configuration
//@EnableAuthorizationServer
//public class OAuth2AuthorizationServer2 extends AuthorizationServerConfigurerAdapter {
//
//    /**
//     * 注入AuthenticationManager ，密码模式用到
//     */
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    /**
//     * 对Jwt签名时，增加一个密钥
//     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
//     */
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("test-secret");
//        return converter;
//    }
//
//    /**
//     * 设置token 由Jwt产生，不使用默认的透明令牌
//     */
//    @Bean
//    public JwtTokenStore jwtTokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints
//                .authenticationManager(authenticationManager)
//                .tokenStore(jwtTokenStore())
//                .accessTokenConverter(accessTokenConverter());
//    }
//
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("client_id_01")
//                .secret("$2a$10$OlspvduHdbBzm5OenfeaAeSFMFKOXtq6mGzPwjaN/seQqio7pcFEG")
//                .scopes("all")
//                //设置支持[密码模式、授权码模式、token刷新]
//                .authorizedGrantTypes(
//                        "password",
//                        "authorization_code",
//                        "refresh_token");
//    }
//
//    @Bean
//    public KeyPair keyPair() {
//        // 文件存储密码："Wabc@2023"
//        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt_keystore.jks"), "Wabc@2023".toCharArray());
//        // 私钥密码
//        KeyPair keyPair = factory.getKeyPair("jwt", "Wabc@2023".toCharArray());
//        return keyPair;
//    }
//}
