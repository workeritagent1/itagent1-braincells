package org.wabc.gateway.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.wabc.gateway.result.Result;
import org.wabc.gateway.service.SysPermissionServiceClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ReactiveAuthorizationManager<AuthorizationContext> 接口是 Spring Security的一个核心接口，
 * 用于进行基于响应式编程模型的权限验证和授权控制。它负责对访问请求进行授权验证，并决定是否允许访问受保护的资源。
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-08
 */
@Component
@Slf4j
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    SysPermissionServiceClient sysPermissionServiceClient;

    /**
     * 这个方法覆盖了ReactiveAuthorizationManager接口中的check方法。它接收两个参数：
     * Mono<Authentication>：一个Mono类型的认证信息，表示当前用户的身份和凭据。
     * AuthorizationContext：一个包含有关当前请求和资源的上下文对象，可以通过它获取相关的请求信息和资源信息。
     * 方法返回一个 Mono<AuthorizationDecision>对象，用于表示授权决策结果。Mono是 Reactor框架中的一种响应式类型，表示异步计算结果。
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        return Mono.just(new AuthorizationDecision(true));
    }

    public Mono<AuthorizationDecision> check2(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();

        // 预检请求放行;这段代码的目的是处理 OPTIONS 请求的授权逻辑。由于 OPTIONS 请求主要用于预检和协商 CORS，
        // 往往不需要进行具体的权限验证，通常会直接允许 OPTIONS 请求通过。
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 【声明定义】Ant路径匹配模式，“请求路径”和缓存中权限规则的“URL权限标识”匹配
        PathMatcher pathMatcher = new AntPathMatcher();

        String path = request.getURI().getPath();
        // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14396990.html
        // 如：POST:/authorization/oauth/token
        String restfulPath = request.getMethodValue() + ":" + path;

        log.info("请求方法:RESTFul请求路径：{}", restfulPath);
        // http://localhost:8001/system/sysPermission/loadPermissionRoles
        // http://system/sysPermission/loadPermissionRoles
        // 不写为异步的回报： block()/blockFirst()/blockLast() are blocking, which is not supported in thread parallel-
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // WebFlux异步调用，同步会报错
        Future future = executorService.submit(() -> sysPermissionServiceClient.loadPermissionRoles());
        Result<String> result = null;
        try {
            result = (Result<String>)future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        log.info("result:{}", JSONUtil.toJsonStr(restfulPath));

        // 缓存取【URL权限标识->角色集合】权限规则
        boolean needToCheck = false;
        JSONObject permissionRoles = JSONUtil.parseObj(result.getData());
        List<String> roles = new ArrayList<>();
        for (Map.Entry<String, Object> permRoles : permissionRoles) {
            String perm = permRoles.getKey(); // URL权限标识
            if (pathMatcher.match(perm, restfulPath)) {
                roles.addAll(Convert.toList(String.class, permRoles.getValue()));
                if (needToCheck == false) {
                    needToCheck = true;
                }
                break;
            }
        }
        // 没有设置权限规则放行；注：如果默认想拦截所有的请求请移除needToCheck变量逻辑即可，根据需求定制
        if (needToCheck == false) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断用户JWT中携带的角色是否有能通过权限拦截的角色
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    log.info("用户权限（角色） : {}", authority);
                    String roleCode = StrUtil.removePrefix(authority,"ROLE_");
                    if ("ROOT".equals(roleCode)) { // 如果是超级管理员则放行
                        return true;
                    }
                    return CollectionUtil.isNotEmpty(roles) && roles.contains(roleCode); // 用户角色中只要有一个满足则通过权限校验
                }).map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}
