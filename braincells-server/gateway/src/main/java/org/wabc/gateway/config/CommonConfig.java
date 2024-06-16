package org.wabc.commons.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Class description.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-04-25
 */
@Configuration
public class CommonConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
