package Billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Configuration
@SpringBootApplication
@EnableAsync
@RestController
@ComponentScan({"Billing","Billing.Aspects"})
@EnableJpaRepositories(basePackages = {"Billing.Repositories"})
@EntityScan(basePackages = {"Billing.Entities", "Billing.Beans"})
@EnableScheduling
@EnableAutoConfiguration
@ConfigurationProperties
@EnableConfigurationProperties
public class Application {
    @RequestMapping("/greeting2")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }

}

// docker run -d -p 8080:8080 --name webserver test