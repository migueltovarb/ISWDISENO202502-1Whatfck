package com.vehiculos.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI para documentación automática de la API
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Vehículos")
                        .description("""
                                API REST para la gestión completa de vehículos que permite:
                                
                                - Operaciones CRUD completas para vehículos
                                - Búsquedas avanzadas por diferentes criterios
                                - Gestión de disponibilidad de vehículos
                                - Estadísticas y reportes
                                - Paginación en listados
                                
                                La API utiliza MongoDB como base de datos y proporciona 
                                documentación automática mediante Swagger/OpenAPI.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("dev@vehiculos.com")
                                .url("https://github.com/vehiculos/api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080/api").description("Servidor de desarrollo"),
                        new Server().url("https://api.vehiculos.com/v1").description("Servidor de producción")))
                ;
    }
}