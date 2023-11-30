package com.leomac00.MyMediaTracker.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customSwagger() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyMediaTracker")
                        .version("v1")
                        .description("A media rating application, used for tracking movies, games, books and all sorts of media consuming by users.")
                        .license(new License()
                                .name("MIT"))
                );
    }
}
