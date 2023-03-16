package it.jorge.protectora.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProtectoraConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Protectora API")
                        .description("Ejemplo de API REST")
                        .contact(new Contact()
                                .name("Jorge")
                                .email("Jorge@gamail.com")
                                .url("https://Jorge.com"))
                        .version("1.0"));


    }
}