package com.cine.Back_Cine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Cine")
                        .version("1.0")
                        .description("API REST para gestión de películas y salas de cine")
                        .contact(new Contact()
                                .name("Back_Cine")
                                .email("cine-Backend@prueba.com")));
    }
}