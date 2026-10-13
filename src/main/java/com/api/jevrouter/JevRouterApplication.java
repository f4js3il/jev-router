package com.api.jevrouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JevRouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JevRouterApplication.class, args);
    }

}
