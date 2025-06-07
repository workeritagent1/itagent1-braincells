package org.wabc.auth.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wabc.commons.security.InternalApiSigner;

import java.util.UUID;


/**
 * FeignConfig
 * -----------
 * 用于为所有Feign请求自动按标准规则加认证Header
 */
@Configuration
public class FeignConfig {
    @Value("${service.internal.access-key}")
    private String accessKey;

    @Value("${service.internal.secret-key}")
    private String secretKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {

            String fullUrl = template.url();
            String path = fullUrl.contains("?") ? fullUrl.substring(0, fullUrl.indexOf('?')) : fullUrl;

            // 2. 生成认证参数
            long timestamp = System.currentTimeMillis();
            String nonce = UUID.randomUUID().toString();

            String body = ""; // 支持POST可手动扩展

            String dataToSign = InternalApiSigner.buildDataToSign(
                    template.method(),
                    path,
                    timestamp,
                    nonce,
                    body
            );

            String signature = InternalApiSigner.sign(dataToSign, secretKey);


            // 标记为内部服务流量
            template.header("X-Internal-Call", "true");
            // 填充所有认证Header
            template.header("X-Timestamp", String.valueOf(timestamp));
            template.header("X-Nonce", nonce);
            template.header("X-Signature", signature);
            template.header("X-Access-Key", accessKey);
        };
    }

}
