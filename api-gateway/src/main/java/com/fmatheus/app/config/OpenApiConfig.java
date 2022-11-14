package com.fmatheus.app.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
public class OpenApiConfig {

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters configParameters, RouteDefinitionLocator locator) {

        var definitions = locator.getRouteDefinitions().collectList().block();

        Objects.requireNonNull(definitions).stream()
                .filter(filter -> filter.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    var name = routeDefinition.getId();
                    configParameters.addGroup(name);
                    GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**")
                            .group(name)
                            .build();
                });

        return new ArrayList<>();
    }

}
