package Tutorials.Tutorial_2;
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
@ComponentScan({"Tutorials"})
@EnableJpaRepositories(basePackages ={ "Tutorials.Tutorial_3"})
@EntityScan(basePackages = "Tutorials.Tutorial_3")
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