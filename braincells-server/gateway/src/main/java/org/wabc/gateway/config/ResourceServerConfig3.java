//package org.wabc.gateway.config;
//
//import cn.hutool.core.codec.Base64;
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.io.IoUtil;
//import cn.hutool.json.JSONObject;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
//import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
//import reactor.core.publisher.Mono;
//
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.List;
//
///**
// * 资源服务器配置
// *
// * @author haoxr
// * @date 2020/05/01
// */
//@ConfigurationProperties(prefix = "security")
//@RequiredArgsConstructor
//@Configuration
//@EnableWebFluxSecurity
//@Slf4j
//public class ResourceServerConfig3 {
//
//    private final ResourceServerManager resourceServerManager;
//
//    @Setter
//    private List<String> ignoreUrls;
//
//    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
//    private String jwkSetUri;
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//
//        if (ignoreUrls == null) {
//            log.error("网关白名单路径读取失败：Nacos配置读取失败，请检查配置中心连接是否正确！");
//        }
//
//        http
//                .oauth2ResourceServer()
//                .jwt()
//                .jwtAuthenticationConverter(jwtAuthenticationConverter())
//                .publicKey(rsaPublicKey())   // 本地加载公钥
////        .jwkSetUri(jwkSetUri)  // 远程获取公钥，默认读取的key是spring.security.oauth2.resourceserver.jwt.jwk-set-uri
//        ;
//        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
//        http.authorizeExchange()
//                .pathMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
//                .anyExchange().access(resourceServerManager)
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler()) // 处理未授权
//                .authenticationEntryPoint(authenticationEntryPoint()) //处理未认证
//                .and().csrf().disable();
//
//        return http.build();
//    }
//
//    /**
//     * 自定义未授权响应
//     */
//    @Bean
//    ServerAccessDeniedHandler accessDeniedHandler() {
//        return (exchange, e) -> {
//            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
//                    .flatMap(response -> writeResponse(response, "访问未授权"));
//            return mono;
//        };
//    }
//
//    /**
//     * token无效或者已过期自定义响应
//     */
//    @Bean
//    ServerAuthenticationEntryPoint authenticationEntryPoint() {
//        return (exchange, e) -> {
//            // Mono.defer 是一个用于延迟创建 Mono 的操作符。
//            // Mono.just() 是一个简单的创建包含给定元素的 Mono 的操作
//            // flatMap 操作将这两个异步操作连接在一起，确保在第一个操作完成后再执行第二个操作。
//            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
//                    // "token无效或已过期"
//                    .flatMap(response -> writeResponse(response, "token无效或已过期"));
//            return mono;
//        };
//    }
//    @SneakyThrows
//    public Mono<Void> writeResponse(ServerHttpResponse response, String errorMsg) {
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-cache");
//
//        JSONObject body = new JSONObject();
//        body.set("code", HttpStatus.UNAUTHORIZED);
//        body.set("msg", errorMsg);
//        DataBuffer buffer = response.bufferFactory().wrap(body.toString().getBytes(StandardCharsets.UTF_8));
//
//        return response.writeWith(Mono.just(buffer));
//    }
//
//    /**
//     * @link https://blog.csdn.net/qq_24230139/article/details/105091273
//     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
//     * 需要把jwt的Claim中的authorities加入
//     * 方案：重新定义权限管理器，默认转换器JwtGrantedAuthoritiesConverter
//     */
//    @Bean
//    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
//
//    /**
//     * 本地获取JWT验签公钥
//     */
//    @SneakyThrows
//    @Bean
//    public RSAPublicKey rsaPublicKey() {
//        Resource resource = new ClassPathResource("jwt_keystore.pub");
//        InputStream is = resource.getInputStream();
//        String publicKeyData = IoUtil.read(is).toString();
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec((Base64.decode(publicKeyData)));
//
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        return rsaPublicKey;
//    }
//
//}
