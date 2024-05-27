package com.example.bookingapp.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {

        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Server productionServer = new Server();
        productionServer.setUrl("http://some.prod.url");
        productionServer.setDescription("Production env");

        Contact contact = new Contact();
        contact.setName("Rost");
        contact.setEmail("my_mail@somemail.ru");

        Info info = new Info()
                .title("Booking API")
                .version("1.0.0")
                .contact(contact)
                .description("API for booking room to hotel")
                .termsOfService("http://some.url");

        return new OpenAPI().info(info).servers(List.of(localhostServer, productionServer));

    }
}
