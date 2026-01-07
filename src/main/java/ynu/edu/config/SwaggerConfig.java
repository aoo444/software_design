package ynu.edu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI createRestApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("智慧校园能耗监测与管理平台API")
                        .description("基于Spring Boot 3 + MyBatis-Plus的能耗监测平台接口文档")
                        .version("1.0")
                        .contact(new Contact().name("").email("")));
    }
}