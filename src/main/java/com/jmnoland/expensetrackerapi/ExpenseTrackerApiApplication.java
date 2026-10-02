package com.jmnoland.expensetrackerapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
<<<<<<< Updated upstream
import io.swagger.v3.oas.annotations.info.Info;
=======
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
>>>>>>> Stashed changes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "ExpenseTracker API",
                version = "1.1.0",
                description = "API documentation for Tracking expenses"
<<<<<<< Updated upstream
        )
)
=======
        ),
        security = {
                @SecurityRequirement(name = "apiKeyAuth"),
                @SecurityRequirement(name = "clientId")
        }
)
@SecurityScheme(name = "apiKeyAuth", type = SecuritySchemeType.HTTP, scheme = "basic",
        description = "Username: API key, Password: API secret")
@SecurityScheme(name = "clientId", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
        paramName = "x-client-id")
>>>>>>> Stashed changes
@EnableScheduling
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class ExpenseTrackerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApiApplication.class, args);
    }
}
