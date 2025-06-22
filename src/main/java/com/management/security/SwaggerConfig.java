package com.management.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI().info(new Info()
                .title("Employee Management API")
                .version("1.0.0")
                .description("Spring Boot REST API for managing employees, departments, and roles.")
                .contact(new Contact().name("Anish Kumar")
                        .email("kumaranish0815@gmail.com")
                        .url("https://www.linkedin.com/in/anish-kumar-a2b457219/")))
                .servers(List.of(new Server().url("http://localhost:8080").description("development")
                        ,new Server().url("http://localhost:8081").description("production"))
                ).addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                    .components(
                     new Components()
                    .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                    )
            );
    }













//        return new OpenAPI()
//                .info(new Info().title("Employee Management API")
//                        .version("1.0.0")
//                        .description("Spring Boot REST API for managing employees, departments, and roles.")
//                        .contact(new Contact()
//                                .name("Anish Kumar")
//                                .email("kumaranish0815@gmail.com")
//                                .url("https://github.com/AnishKumar-1"))
//            )
//            .addSecurityItem(new SecurityRequirement().addList(name:"bearerAuth"))
//            .components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
//                        .name("Authorization").type(SecurityScheme.In.HEADER).scheme("bearer").bearerFormat("JWT")));
//
//}

}
