package com.fmatheus.app.config;

import com.fmatheus.app.controller.constant.PropertiesConstant;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value(PropertiesConstant.OPENAPI_DESCRIPTION) String appDesciption,
            @Value(PropertiesConstant.OPENAPI_VERSION) String appVersion,
            @Value(PropertiesConstant.OPENAPI_TITLE) String appTitle
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title(appTitle)
                        .version(appVersion)
                        .description(appDesciption)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
