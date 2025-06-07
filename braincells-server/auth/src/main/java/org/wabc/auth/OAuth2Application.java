package org.wabc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * OAuth2.0认证中心 Authorization Server
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRedisHttpSession
public class OAuth2Application {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2Application.class, args);
    }

}
