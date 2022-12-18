package com.fmatheus.app.config;

import com.fmatheus.app.controller.util.ApplicationUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class OpenApiConfig {

    private final ApplicationUtil application;

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title(this.application.getOpenApiTitle())
                        .version(this.application.getOpenApiVersion())
                        .description(this.application.getOpenApiDescription())
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }


}
