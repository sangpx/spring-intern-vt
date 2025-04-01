package com.demo.project_intern.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${openapi.service.title}") String title,
                           @Value("${openapi.service.version}") String version,
                           @Value("${openapi.service.description}") String description,
                           @Value("${openapi.service.serverUrl}") String serverUrl,
                           @Value("${openapi.service.serverName}") String serverName,
                           @Value("${openapi.license.name}") String nameLicense,
                           @Value("${openapi.license.url}") String urlLicense) {
        return new OpenAPI()
                .info(new Info()
                    .title(title)
                    .version(version)
                    .description(description)
                    .license(new License().name(nameLicense).url(urlLicense)))
                .servers(List.of(new Server()
                        .url(serverUrl)
                        .description(serverName)))
//                .components(new Components().addSecuritySchemes(
//                            "bearerAuth",
//                            new SecurityScheme()
//                                   .type(SecurityScheme.Type.HTTP)
//                                   .scheme("bearer")
//                                   .bearerFormat("JWT")
//
//                   )).security(List.of(new SecurityRequirement().addList("bearerAuth")))
                ;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("api-service")
                .packagesToScan("com.demo.project_intern.controller") //scan all controllers in package
                .build();
    }
}