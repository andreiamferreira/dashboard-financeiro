package com.dashboard.dashboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class OpenApiConfig {

    @Bean
    public OpenAPI dashboardAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dashboard API")
                        .description(
                                "Dashboard para controle financeiro construído durante a primeira fase do programa GoTech, uma parceira Zallpy/Unicred")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .email("andreia.ferreira@zallpy.com")
                                .url("https://git.zallpylabs.com/andreia.ferreira/dashboard-financeiro-projeto-final")));

    }

}
