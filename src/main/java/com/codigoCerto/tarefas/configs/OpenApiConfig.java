package com.codigoCerto.tarefas.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "API Tarefas", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {
                @Server(url = "https://trilhabackendjr-jun15-production-e24a.up.railway.app", description = "Produção"),
                @Server(url = "http://localhost:8080", description = "Local")
        }
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {

        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/swagger-ui/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
                        .resourceChain(false);
        }
}