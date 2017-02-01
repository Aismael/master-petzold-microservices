package Order;

import Order.Loader.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.io.IOException;


@Configuration
@SpringBootApplication
@EnableAsync
@RestController
@ComponentScan({"Order","Order.Aspects","Order.Loader"})
@EnableJpaRepositories(basePackages = {"Order.Repositories"})
@EntityScan(basePackages = {"Order.Entities", "Order.Beans"})
@EnableScheduling
@EnableAutoConfiguration
@ConfigurationProperties
@EnableConfigurationProperties
public class Application {
    @RequestMapping("/greeting")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MyConfig myConfig() {
        final MyConfig myConfig = new MyConfig();
        return myConfig;
    }
}


// docker run -d -p 8080:8080 --name webserver test