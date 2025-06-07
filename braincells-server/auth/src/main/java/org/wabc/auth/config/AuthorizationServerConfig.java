package org.wabc.auth.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.UUID;

/**
 * OAuth 2.1 授权服务器核心配置
 * - 强制 PKCE
 * - OIDC 支持
 * - 客户端与授权信息 JDBC 持久化（数据库表 oauth2_registered_client、oauth2_authorization）
 * 当前maven是老版本，新版代码可参考
 * https://docs.spring.io/spring-authorization-server/reference/getting-started.html
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationServerConfig {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationServerConfig.class);
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     *  // 1. OAuth2核心端点安全链（最高优先级）
     * 这一段定义只保护OAuth2端点，包括/oauth2/authorize、/oauth2/token、/oauth2/jwks和OIDC等所有规范端点。
     * 用于保证只有认证用户才能访问这些授权相关接口。
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        // 启用OpenID Connect协议支持（OIDC必须在业务现代架构下开启）
        authorizationServerConfigurer.oidc(Customizer.withDefaults());

        // 只“拦截”所有OAuth2.1核心端点，其它不处理
        // // 必须禁用CSRF，否则Spring Security 默认启用 CSRF 保护，所有状态变更请求（POST、PUT、DELETE 等）都需要有效的 CSRF 令牌；
        // 未携带的请求会报403错误。
        http.requestMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated() // 这些端点都得是认证用户
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login.html")) // 未认证跳转登录页面
                .and()
                .apply(authorizationServerConfigurer);

        return http.build();
    }


    //

    /**
     * 2. 默认安全链（处理除OAuth2端点外所有常规请求）
     * 新增自定义拦截规则（如放行静态资源），只需在这里改。
     * 登录页：formLogin会自动处理/login请求。
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(
                                "/index.html",
                                "/login.html",
                                "/callback.html",
                                "/static/**"
                        ).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")  //// 表单提交地址,不需要补充/auth
                        .successHandler(authenticationSuccessHandler())   // 用你自定义的
                        .failureHandler(authenticationFailureHandler())   // 用你自定义的
                )
                .csrf().disable() // // 若接口纯后端和本地静态页面可disable
                .authenticationProvider(customAuthenticationProvider);
        return http.build();
    }

//    // 可选：用户自定义AuthenticationManager（有些场景下Authorization Server用得到）
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationProvider(customAuthenticationProvider)
//                .build();
//    }


    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws ServletException, java.io.IOException {
                log.info("登录成功：用户={}，请求来源IP={}",
                        authentication.getName(),
                        request.getRemoteAddr());
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    // 它已实现了“登录失败后重定向到/login?error，自动带上异常信息”的标准流程，你要做的只是加点自定义
    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws java.io.IOException, ServletException {
                log.warn("登录失败：用户名={}，请求来源IP={}，原因={}",
                        request.getParameter("username"),
                        request.getRemoteAddr(),
                        exception.getMessage());
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }

// 仅当你要完全自定义登录异常响应（如全部JSON返回），
//    public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
//        @Override
//        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
//                throws IOException, ServletException {
//            response.setContentType("application/json;charset=UTF-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("{\"msg\":\"登录失败，请检查账号密码！\"}");
//        }
//    }

    /**
     * OAuth2 客户端信息持久化Bean
     * - 映射 oauth2_registered_client 表
     *
     * 只要你在 oauth2_registered_client 表的 client_settings 字段填写了
     * {"require_proof_key":true,"require_authorization_consent":true}，就表示该客户端已开启了“强制PKCE”！
     */
    // 客户端注册持久化，表 oauth2_registered_client
    @Bean
    public RegisteredClientRepository registeredClientRepository(DataSource dataSource) {
        JdbcRegisteredClientRepository repo = new JdbcRegisteredClientRepository(new JdbcTemplate(dataSource));
        // 初始化一个client（推荐初次导入一次，在运维脚本/初始化代码里进行）
        if(repo.findByClientId("demo-client") == null){
            // 使用密码编码器对客户端密码进行编码
            String encodedSecret = passwordEncoder.encode("demo123456");
            RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("demo-client")
                    .clientSecret("") // .clientSecret(encodedSecret) // 推荐用安全加密方式  //  .clientSecret("{noop}demo123456")
                    // // web浏览器端：一定不能设置密码和method
                    .clientSecret("")  // 必须为空字符串，不能null（有些表结构不让null）
                    .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // 关键！一定要加
                    .redirectUri("http://localhost:10001/auth/callback.html")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .scope("openid")
                    .scope("profile")
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(true)  // 需要用户同意
                            .requireProofKey(true) // 强制pkce
                            .build())
                    .build();
            repo.save(client);
        }
        return repo;
    }
    /**
     * 授权信息存储（授权码/access_token等）
     * - 映射 oauth2_authorization 表
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(DataSource dataSource, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(new JdbcTemplate(dataSource), registeredClientRepository);
    }


    /**
     *
     * ProviderSettings 主要用于配置issuer（授权服务器签发方标识），
     * 这个值对所有OAuth2 OIDC客户端和资源服务器统一且极其重要，必须在生产环境下显式配置成你的对外服务真实域名地址！
     *
     * 本地测试可用 http://localhost:9000，但正式环境推荐用https且是对外可识别域名。
     * issuer和实际暴露/转发路径要一致。微服务环境下通常建议取网关暴露的外部地址。
     * @return
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:10001/auth")   // 替换成你自己的地址
                .build();

//        .issuer("http://localhost:10001/auth")
//                .tokenRevocationEndpoint("/oauth2/revoke") // 添加令牌撤销端点
//                .tokenIntrospectionEndpoint("/oauth2/introspect") // 添加令牌检查端点
//                .build();
    }

    //  SAS 0.4.4版本不支持。
//    /**
//     *  Spring Authorization Server（SAS） 配的专用 ObjectMapper，只影响 OAuth2Authorization
//     *  等授权信息落库时的序列化/反序列化（通常是存oauth2_authorization这类表字段）。
//     * @return
//     */
//    @Bean
//    public ObjectMapper authzObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 注册 SAS 官方模块，支持 OAuth2Authorization 等序列化
//        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
//        // 时间模块支持
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        // （可选）如果还有其它定制，参照你自己的具体情况增加
//        return objectMapper;
//    }
//
//    // 关键：设置给 SAS 使用
//    @Bean
//    public OAuth2AuthorizationService authorizationService(
//            DataSource dataSource,
//            RegisteredClientRepository registeredClientRepository,
//            ObjectMapper authzObjectMapper) {
//        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper =
//                new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
//        rowMapper.setObjectMapper(authzObjectMapper);
//        return new JdbcOAuth2AuthorizationService(new JdbcTemplate(dataSource), registeredClientRepository, rowMapper);
//    }

    /**
     * 自定义Session序列化方式（JSON格式）,针对HttpSession对象在Redis里的读写；避免JDK序列化带来未来不可控bug;
     */
//    @Bean
//    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
//        return new GenericJackson2JsonRedisSerializer();
//    }

    // 关于 JWKSource/JwtEncoder/JwtDecoder 相关 Bean，来自于JwtKeyConfig
}