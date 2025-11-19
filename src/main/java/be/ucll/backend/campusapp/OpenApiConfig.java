package be.ucll.backend.campusapp;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI campusReservationAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Campus Reservation API")
                        .description("API for managing campuses, lokalen, users, and reservations.")
                        .version("1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("API Docs")
                        .url("http://localhost:8080/swagger-ui.html"));
    }
}
