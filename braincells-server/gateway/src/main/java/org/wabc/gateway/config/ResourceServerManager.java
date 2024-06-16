package org.wabc.gateway.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.model.v2.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
import org.wabc.commons.constant.GlobalConstants;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ReactiveAuthorizationManager<AuthorizationContext> 接口是 Spring Security的一个核心接口，
 * 用于进行基于响应式编程模型的权限验证和授权控制。它负责对访问请求进行授权验证，并决定是否允许访问受保护的资源。
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-08
 */
@Component
// @RequiredArgsConstructor注解时，Lombok会为这个类生成一个构造函数，这个构造函数包含所有被标记为final的字段作为参数。
@RequiredArgsConstructor
@Slf4j
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    // 使用构造函数方式依赖注入
    private final RedisTemplate redisTemplate;
    private final GatewayConfig config;

    /**
     * 这个方法覆盖了ReactiveAuthorizationManager接口中的check方法。它接收两个参数：
     * Mono<Authentication>：一个Mono类型的认证信息，表示当前用户的身份和凭据。
     * AuthorizationContext：一个包含有关当前请求和资源的上下文对象，可以通过它获取相关的请求信息和资源信息。
     * 方法返回一个 Mono<AuthorizationDecision>对象，用于表示授权决策结果。Mono是 Reactor框架中的一种响应式类型，表示异步计算结果。
     */
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();

        // 预检请求放行;这段代码的目的是处理 OPTIONS 请求的授权逻辑。由于 OPTIONS 请求主要用于预检和协商 CORS，
        // 往往不需要进行具体的权限验证，通常会直接允许 OPTIONS 请求通过。
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 【声明定义】Ant路径匹配模式，“请求路径”和缓存中权限规则的“URL权限标识”匹配
        PathMatcher pathMatcher = new AntPathMatcher();

        // "/gateway/auth2/oauth/token" ==> "/auth2/oauth/token"
        String path = request.getURI().getPath();
        path = path.substring(config.getContextPath().length());

        // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14396990.html
        // 如：==> POST:/authorization/oauth/token
        String restfulPath = request.getMethodValue() + ":" + path;

        log.info("请求方法:RESTFul请求路径：{}", restfulPath);
        // http://localhost:8001/system/sysPermission/loadPermissionRoles
        // http://system/sysPermission/loadPermissionRoles

        // 如果token以"bearer "为前缀，到此方法里说明JWT有效即已认证
        String token = request.getHeaders().getFirst(GatewayConstants.AUTHORIZATION);
        if (StrUtil.isNotBlank(token) && StrUtil.startWithIgnoreCase(token, GatewayConstants.BEARER_SPACE)) {
            /*
             if (path.contains("/app-api")) {
                // 商城移动端请求需认证不需鉴权放行（根据实际场景需求）
                return Mono.just(new AuthorizationDecision(true));
             }
             */
        } else {
            return Mono.just(new AuthorizationDecision(false));
        }
        /**
         * 鉴权开始
         * 缓存取 [URL权限-角色集合] 规则数据
         * urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
         *   {
         *       "PUT:/system/sysUser/*": [
         *           "webmaster,sysadmin"
         *       ],
         *       "DELETE:/system/sysUser/*": [
         *           "aigcadmin"
         *       ],
         *       "GET:/system/sysUser/*": [
         *           "sysadmin"
         *       ],
         *       "POST:/system/sysUser/*": [
         *           "blogadmin"
         *       ]
         *   }
         */
        Map<String, Object> urlPermRolesRules = redisTemplate.opsForHash().entries(GlobalConstants.URL_PERM_ROLES_KEY);

        log.info("urlPermRolesRules==>\n"+JSONUtil.toJsonPrettyStr(urlPermRolesRules));

        // 根据请求路径获取有访问权限的角色列表
        List<String> authorizedRoles = new ArrayList<>(); // 拥有访问权限的角色 // 为空表示不需要鉴权；默认未设置拦截规则不需鉴权

        // 使用Java 8的Stream API进行路径匹配
        urlPermRolesRules.entrySet().stream()
                .filter(entry -> pathMatcher.match(entry.getKey(), restfulPath))
                .forEach(entry -> {
                    List<String> roles = Convert.toList(String.class, entry.getValue());
                    authorizedRoles.addAll(roles);
                });

        // 没有设置拦截规则放行
        if (authorizedRoles.isEmpty()) {
            log.debug("对该url: {}没有设置拦截规则放行",restfulPath);
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断JWT中携带的用户角色是否有权限访问
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    // ROLE_ADMIN移除前缀ROLE_得到用户的角色编码ADMIN
                    String roleCode = StrUtil.removePrefix(authority,"ROLE_");
                    if (GlobalConstants.ROOT_ROLE_CODE.equals(roleCode)) {
                        return true; // 如果是超级管理员则放行
                    }
                    boolean hasAuthorized = authorizedRoles.contains(roleCode);
                    log.debug("有权访问的角色: {}   当前用户遍历到的角色:   {}",JSONUtil.toJsonStr(authorizedRoles),authority);
                    return hasAuthorized;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }

    private Result<String> getPermission(){
        String data = "{\n" +
                "    \"PUT:/system/sysUser/*\": [\n" +
                "        \"webmaster\"\n" +
                "    ],\n" +
                "    \"DELETE:/system/sysUser/*\": [\n" +
                "        \"aigcadmin\"\n" +
                "    ],\n" +
                "    \"GET:/system/sysUser/*\": [\n" +
                "        \"sysadmin\"\n" +
                "    ],\n" +
                "\t\"GET:/gateway/system/sysPermission/loadPermissionRoles\": [\n" +
                "        \"aigcadmin\"\n" +
                "    ],\n" +
                "    \"POST:/system/sysUser/*\": [\n" +
                "        \"blogadmin\"\n" +
                "    ]\n" +
                "}";
       String s = "{\"code\":0,\"message\":\"success\",\"data\":\"{\\\"PUT:/system/sysUser/*\\\":[\\\"webmaster\\\"],\\\"DELETE:/system/sysUser/*\\\":[\\\"aigcadmin\\\"],\\\"GET:/system/sysUser/*\\\":[\\\"sysadmin\\\"],\\\"POST:/system/sysUser/*\\\":[\\\"blogadmin\\\"]}\"}";
        JSONObject json = JSONUtil.parseObj(s);
        Result result = new Result(json.getInt("code"),json.getStr("message"),data);
        log.info(JSONUtil.parse(result.getData()).toStringPretty());
       return result;

    }

    public static void main(String[] args) {
        String s = "{\"code\":0,\"message\":\"success\",\"data\":\"{\\\"PUT:/system/sysUser/*\\\":[\\\"webmaster\\\"],\\\"DELETE:/system/sysUser/*\\\":[\\\"aigcadmin\\\"],\\\"GET:/system/sysUser/*\\\":[\\\"sysadmin\\\"],\\\"POST:/system/sysUser/*\\\":[\\\"blogadmin\\\"]}\"}";
        JSONObject json = JSONUtil.parseObj(s);
        Result result = new Result(json.getInt("code"),json.getStr("message"),json.getStr("data"));
        log.info(JSONUtil.parse(result.getData()).toStringPretty());
        log.info(JSONUtil.parse(result).toStringPretty());
        /*{
                "PUT:/system/sysUser/*": [
                "webmaster,sysadmin"
            ],
                "DELETE:/system/sysUser/*": [
                "aigcadmin"
            ],
                "GET:/system/sysUser/*": [
                "sysadmin"
            ],
                "POST:/system/sysUser/*": [
                "blogadmin"
            ]
        }*/

    }
}
