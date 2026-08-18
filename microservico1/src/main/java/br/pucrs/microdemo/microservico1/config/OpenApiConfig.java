package br.pucrs.microdemo.microservico1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI microservico1OpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservico Academico")
                        .version("1.0")
                        .description("API para cadastro de estudantes, disciplinas e matriculas."))
                .addServersItem(new Server().url("/").description("Servidor atual"));
    }
}
