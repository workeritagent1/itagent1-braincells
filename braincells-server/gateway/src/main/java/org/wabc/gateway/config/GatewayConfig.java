package org.wabc.gateway.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class description.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-06-16
 */
@Component
@Getter
public class GatewayConfig {
    /**
     * 读取网关的路由根配置 gateway
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;
}
