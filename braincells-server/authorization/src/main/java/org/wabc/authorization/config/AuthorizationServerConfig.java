package org.wabc.authorization.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * oauth2标准规范的授权服务器，包括以下功能：
 * 1.验证第三方系统客户端或授权服务器所属系统的直接用户的身份。
 * 2.发放授权码和令牌
 * <p>
 * 执行顺序：
 * 1.configure(ClientDetailsServiceConfigurer clients)
 * 2.configure(AuthorizationServerSecurityConfigurer security)
 * 3.configure(AuthorizationServerEndpointsConfigurer endpoints)
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-30
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    // 管理客户端应用程序的信息和凭据
    @Autowired
    @Qualifier("ClientDetailsServiceImpl")
    private ClientDetailsService clientDetailsService;


    /**
     * 来自于 WebSecurityConfig注入的PasswordEncoder.bean
     * ClientDetails客户端密码加密器。当授权服务器验证客户端时，会使用注入的密码加密器对客户端提交的密码进行加密，并与从数据库加载的已加密密码进行比对。
     * 如果密码匹配成功，则客户端验证通过。
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置客户端信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置授权服务器的安全性相关设置，例如设置访问令牌的密钥、允许的请求方式
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 1.允许客户表单认证,不加的话/oauth/token无法访问
        // 对于CheckEndpoint控制器[框架自带的校验]的/oauth/token端点允许所有客户端发送器请求而不会被Spring-security拦截
        // 开启/oauth/token_key验证端口无权限访问
        // 要访问/oauth/check_token必须设置为permitAll()，但这样所有人都可以访问了，设为isAuthenticated()又导致访问不了，这个问题暂时没找到解决方案
        // 开启/oauth/check_token验证端口认证权限访问
        security.allowFormAuthenticationForClients()
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
                // 客户端密码加密器(已确认)
                .passwordEncoder(passwordEncoder);
    }


    /**
     * 配置授权服务器的端点，例如设置认证管理器、用户详情服务、访问令牌存储
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置jwt内容增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        // 初始化所有的TokenGranter，并且类型为CompositeTokenGranter
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(endpoints);

        // approvalStore(approvalStore())  影响授权码询问客户是否授权页面
        endpoints.authenticationManager(authenticationManager)
                .tokenGranter(new CompositeTokenGranter(tokenGranters))
                .tokenStore(new JwtTokenStore(jwtAccessTokenConverter()))
                .tokenEnhancer(tokenEnhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter())
                .reuseRefreshTokens(true);

        // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
        //  1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
        //  2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的

    }


    /**
     * 初始化所有的TokenGranter
     */
    private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerEndpointsConfigurer endpoints) {
        return new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));

      /*  ClientDetailsService clientDetails = endpoints.getClientDetailsService();
        AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
        AuthorizationCodeServices authorizationCodeServices = endpoints.getAuthorizationCodeServices();
        OAuth2RequestFactory requestFactory = endpoints.getOAuth2RequestFactory();

        List<TokenGranter> tokenGranters = new ArrayList<>();
        // 处理授权码类型的令牌授权请求
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails, requestFactory));
        // 处理刷新令牌类型的令牌授权请求。
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        // 处理隐式授权类型的令牌授权请求。
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory));
        // 处理客户端凭据授权类型的令牌授权请求。
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        if (authenticationManager != null) {
            // 处理密码授权类型的令牌授权请求。
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetails, requestFactory));
        }
        return tokenGranters;*/
    }

    /**
     * 自定义的令牌增强器,充additionalInfo{}内容
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }


    /**
     * JWT访问令牌转换器,使用非对称密钥
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


}
