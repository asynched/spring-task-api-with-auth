package tech.asynched.auth.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration()
public class OpenAPIConfiguration {
  @Bean()
  public OpenAPI customize() {
    var securityName = "bearerAuth";

    return new OpenAPI()
        .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList(securityName))
        .components(new io.swagger.v3.oas.models.Components()
            .addSecuritySchemes(securityName, new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")));
  }
}
