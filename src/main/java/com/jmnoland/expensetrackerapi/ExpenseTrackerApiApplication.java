package com.jmnoland.expensetrackerapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "ExpenseTracker API",
                version = "1.1.0",
                description = "API documentation for Tracking expenses"
        )
)
@EnableScheduling
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class ExpenseTrackerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApiApplication.class, args);
    }
}
