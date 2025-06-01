package br.com.neurotech.challenge.config;

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
                        .title("Neurotech Challenge - Credit Simulation API")
                        .version("1.0")
                        .description("RESTful API for evaluating and simulating credit eligibility for individual clients.")
                        .contact(new Contact()
                                .name("Caio Fernandez Santos Barros")
                                .email("caiofernandez@live.co.uk")
                                .url("https://www.linkedin.com/in/caiofsbarros/")));
    }
}
