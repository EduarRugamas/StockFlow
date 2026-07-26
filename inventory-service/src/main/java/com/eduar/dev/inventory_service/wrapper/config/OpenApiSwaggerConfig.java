package com.eduar.dev.inventory_service.wrapper.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiSwaggerConfig {


    @Bean
    public OpenAPI openApiConfig() {


        Contact contact = new Contact()
                                .name("Eduardo Rugama")
                                .email("juaneduardo021299@hotmail.com");

        License license = new License()
                                .name("Apache License 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Servidor local de desarrollo");

        Info apiInfo = new Info()
                            .title("Inventory Service API")
                            .description("""
                                API REST para la administración de productos,
                                movimientos de inventario y alertas de stock.
                                    """)
                            .version("0.0.1")
                            .contact(contact)
                            .license(license);
        return new OpenAPI()
                        .info(apiInfo)
                        .servers(List.of(localServer));
   }

}
