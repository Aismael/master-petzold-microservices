package Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan({"Order"})
@EnableJpaRepositories(basePackages ={ "Order.Repositories"})
@EntityScan(basePackages = "Order.Entities")
@EnableScheduling
@EnableAutoConfiguration
@ConfigurationProperties
@EnableConfigurationProperties
public class Application {
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

}

// docker run -d -p 8080:8080 --name webserver test