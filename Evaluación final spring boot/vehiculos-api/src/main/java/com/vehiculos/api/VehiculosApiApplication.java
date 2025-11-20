package com.vehiculos.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación de gestión de vehículos
 * Punto de entrada para ejecutar la aplicación Spring Boot
 */
@SpringBootApplication
public class VehiculosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiculosApiApplication.class, args);
        System.out.println("""
                
                🚗 ================================================
                🚗    API de Gestión de Vehículos
                🚗    Aplicación iniciada correctamente
                🚗    
                🚗    🔗 Swagger UI: http://localhost:8080/api/swagger-ui.html
                🚗    📊 API Docs: http://localhost:8080/api/v3/api-docs
                🚗    ❤️  Health Check: http://localhost:8080/api/vehiculos/health
                🚗    🗄️  Mongo Express: http://localhost:8081
                🚗 ================================================
                
                """);
    }
}