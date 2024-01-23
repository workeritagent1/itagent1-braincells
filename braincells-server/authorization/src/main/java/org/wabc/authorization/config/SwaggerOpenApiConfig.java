package org.wabc.authorization.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi 界面配置
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-06
 */
@Configuration
public class SwaggerOpenApiConfig {

    /**
     * 参考 https://blog.csdn.net/Mrqiang9001/article/details/132305015
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("Knife4j OpenApi 3")
                        // 接口文档描述
                        .description("project for all man free from surviving and material well-being life")
                        // 接口文档版本
                        .version("v1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("braincell.wabc").url("https://github.com/braincell")))
                .externalDocs(new ExternalDocumentation()
                        // 额外补充说明
                        .description("API doc")
                        // 额外补充链接
                        .url("https://github.com/Flying9001/springBootDemo/demo-knife4j-openapi3"));
    }
}
