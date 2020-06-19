package com.dev.republica;

import com.dev.republica.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class RepublicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepublicaApplication.class, args);
    }

}
