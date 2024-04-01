package org.wabc.gateway.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName ResourceServerConfig
 * @description: WebFlux vs. Servlet： Reactor 环境使用的是 WebFlux，它是一种非阻塞、
 * 响应式的编程模型，适用于构建高吞吐量的 Web 应用。而 Spring Boot 中通常使用的是 Servlet 环境，这是传统的基于线程池的阻塞式编程模型。
 * @date 2023/12/24 10:47
 */
@Configuration
@EnableWebFluxSecurity
@Slf4j
public class ResourceServerConfig {
    // Lombok @AllArgsConstructor 自动生成了一个包含 ResourceServerManager 参数的构造函数。实现构造函数注入
    @Autowired
    private  ResourceServerManager resourceServerManager;

    // 白名单
    @Value("${ignoreUrls}")
    private List<String> ignoreUrls;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // 白名单配置文件检查
        if (ignoreUrls == null) {
            log.error("网关白名单路径读取失败：Nacos配置读取失败，请检查配置中心连接是否正确！");
        }

        // http.oauth2ResourceServer() 是用于配置 Spring Security 的 OAuth2 资源服务器。
        // 它指定了如何验证和处理传入的请求中的令牌。在这个配置中，可以设置身份验证转换器、自定义错误处理等与 OAuth2 相关的内容。
        //  1.启用和配置 OAuth 2.0 资源服务器。
        //  2.配置资源服务器使用 JWT（JSON Web Token）作为令牌解析机制。
        //  3.添加自定义逻辑来将 JWT 转换为 Spring Security 的 Authentication 对象。
        //  4.配置用于验证 JWT 签名的 RSA 公钥。你需要提供一个 rsaPublicKey() 方法，返回获取到的 RSA 公钥对象。
        // 远程获取公钥，默认读取的key是spring.security.oauth2.resourceserver.jwt.jwk-set-uri
        //  5.认证入口点是当未经身份验证的用户尝试访问受保护资源时，由系统调用的组件。它负责处理认证异常并发送适当的响应给用户。
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                // .publicKey(rsaPublicKey())
                .jwkSetUri(jwkSetUri)
                .and().authenticationEntryPoint(authenticationEntryPoint());

        // http.authorizeExchange()则用于配置请求的授权规则。通过这个配置，你可以决定哪些请求需要进行授权才能访问。
        // 你可以指定路径匹配规则、允许或拒绝访问等。
        //  1.配置白名单
        //  2.配置自定义决策管理器
        //  3.处理未授权
        //  4.处理未授权
        //  5.取消csrf
        http.authorizeExchange()
                .pathMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
                .anyExchange().access(resourceServerManager)
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) // 处理未授权
                .authenticationEntryPoint(authenticationEntryPoint()) //处理未认证
                .and()
                .csrf().disable();

        return http.build();
    }

    /**
     * 未授权自定义响应
     */
    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, e) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> writeResponse(response, "访问未授权"));
            return mono;
        };
    }

    /**
     * token无效或者已过期自定义响应;在身份验证失败时的处理逻辑
     */
    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        // 这段代码是一个使用 Lambda 表达式实现 ServerAuthenticationEntryPoint 接口的匿名实现类。
        // Mono.defer(() -> Mono.just(exchange.getResponse()))
        // Mono.defer 的目的是确保在订阅时执行这个函数，以实现延迟加载。这在响应式编程中很有用，因为它可以根据实际需要动态创建 Mono，而不是在一开始就立即执行。
//        举个例子，如果你不使用 Mono.defer，而是直接使用 Mono.just(exchange.getResponse())，那么 exchange.getResponse()
//        将在创建 Mono 时立即执行，而不是在订阅时。使用 Mono.defer 可以确保在需要时才执行相关的代码，提供更好的灵活性。
        return (exchange, e) -> {
            // Mono.defer 是一个用于延迟创建 Mono 的操作符。
            // Mono.just() 是一个简单的创建包含给定元素的 Mono 的操作
            // flatMap 操作将这两个异步操作连接在一起，确保在第一个操作完成后再执行第二个操作。
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    // "token无效或已过期"
                    .flatMap(response -> writeResponse(response, "token无效或已过期"));
            return mono;
        };
    }

    @SneakyThrows
    public Mono<Void> writeResponse(ServerHttpResponse response, String errorMsg) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-cache");

        JSONObject body = new JSONObject();
        body.set("code", HttpStatus.UNAUTHORIZED);
        body.set("msg", errorMsg);
        DataBuffer buffer = response.bufferFactory().wrap(body.toString().getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    /**
     * @link https://blog.csdn.net/qq_24230139/article/details/105091273
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        // 这段代码是在 Spring Security 的 Reactor（WebFlux）环境中配置 JWT (JSON Web Token) 的认证转换器。
        // 它的作用是将从 JWT 中提取的权限信息映射为 Spring Security 的 GrantedAuthority 实例。
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 通过设置 authorityPrefix 和 authoritiesClaimName 属性，你可以配置生成的权限的前缀和声明中存储权限信息的字段名。
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    /**
     * 本地加载JWT验签公钥
     *
     * @return
     */
    @SneakyThrows
    @Bean
    public RSAPublicKey rsaPublicKey() {
        Resource resource = new ClassPathResource("jwt_keystore.pub");
        InputStream is = resource.getInputStream();
        String publicKeyData = IoUtil.read(is).toString();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec((Base64.decode(publicKeyData)));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        return rsaPublicKey;
    }
}