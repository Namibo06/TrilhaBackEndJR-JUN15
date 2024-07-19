package com.codigoCerto.tarefas.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "API Tarefas", version = "v1"),
        servers = {
                @Server(url = "https://trilhabackendjr-jun15-production-e24a.up.railway.app", description = "Produção"),
                @Server(url = "http://localhost:8080", description = "Local")
        }
)
public class OpenApiConfig {
}