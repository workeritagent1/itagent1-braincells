package org.wabc.system.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wabc.commons.security.InternalApiSigner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * InternalApiAuthFilter
 * ----------------------
 * 基于Redis的分布式nonce认证Filter：严格校验Header，杜绝伪造与重放攻击（群集安全可扩展）。
 */
@Component
public class InternalApiAuthFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(InternalApiAuthFilter.class);
    @Value("${service.internal.access-key}")
    private String accessKey;

    @Value("${service.internal.secret-key}")
    private String secretKey;

    @Autowired
    private StringRedisTemplate redisTemplate;


    // 有效时间窗口，5分钟（单位ms）
    private static final long TIME_WINDOW_MS = TimeUnit.MINUTES.toMillis(5);

    private static final List<String> INTERNAL_URLS = Arrays.asList("auth-user-info");

    /**
     * 匹配内部URL地址，用于判断内部header验证。
     * @param url
     * @return
     */
    private boolean isInternalURL(String url){
        for (String internalUrl : INTERNAL_URLS) {
            if (url.contains(internalUrl)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // === 打印所有请求头，用于调试 ===
        log.info("======= Incoming request headers =======");
        java.util.Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            java.util.Enumeration<String> vEnum = req.getHeaders(name);
            StringBuilder sb = new StringBuilder();
            while (vEnum.hasMoreElements()) {
                sb.append(vEnum.nextElement());
                if (vEnum.hasMoreElements()) sb.append(",");
            }
            log.info("{}: {}", name, sb.toString());
        }
        log.info("=======================================");

        // 拦截/internal路由
        if (isInternalURL(req.getRequestURI())) {
            try {
                if (!"true".equals(req.getHeader("X-Internal-Call"))) {
                    unauthorized(resp, "非内部调用");
                    return;
                }
                String requestAccessKey = req.getHeader("X-Access-Key");
                String timestampStr = req.getHeader("X-Timestamp");
                String nonce = req.getHeader("X-Nonce");
                String signature = req.getHeader("X-Signature");
                if (!StringUtils.hasText(requestAccessKey)
                        || !StringUtils.hasText(timestampStr)
                        || !StringUtils.hasText(nonce)
                        || !StringUtils.hasText(signature)) {
                    unauthorized(resp, "缺少认证头");
                    return;
                }
                if (!accessKey.equals(requestAccessKey)) {
                    unauthorized(resp, "AccessKey无效");
                    return;
                }
                long timestamp;
                try {
                    timestamp = Long.parseLong(timestampStr);
                } catch (Exception e) {
                    unauthorized(resp, "时间戳格式错误");
                    return;
                }
                long now = System.currentTimeMillis();
                if (Math.abs(now - timestamp) > TIME_WINDOW_MS) {
                    unauthorized(resp, "请求超时");
                    return;
                }

                // Redis setIfAbsent保证分布式nonce唯一，+1s保证宽容并发窗口
                String redisKey = "internal_api:nonce:" + nonce;
                Boolean ok = redisTemplate.opsForValue().setIfAbsent(redisKey, "1", TIME_WINDOW_MS + 1000, TimeUnit.MILLISECONDS);
                if (ok == null || !ok) {
                    unauthorized(resp, "重复请求");
                    return;
                }

                String method = req.getMethod();
                String url = req.getRequestURI();
                String body = ""; // 如需校验body扩展body缓存包装器
                String dataToSign = InternalApiSigner.buildDataToSign(method, url, timestamp, nonce, body);
                String expectedSign = InternalApiSigner.sign(dataToSign, secretKey);
                if (!expectedSign.equals(signature)) {
                    unauthorized(resp, "签名无效");
                    return;
                }
            } catch (Exception e) {
                unauthorized(resp, "认证异常: " + e.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }


    private void unauthorized(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"code\":401,\"message\":\"" + msg + "\"}");
    }
}