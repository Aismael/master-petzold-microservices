package cookbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConfigurationProperties
@EnableConfigurationProperties
@EntityScan(basePackages = {"cookbook.Entities"})

@SpringBootApplication
public class CookbookApplication {

	@RequestMapping("/")
    public String hello(){
	    return "Hello World";
    }

	public static void main(String[] args) {
		SpringApplication.run(CookbookApplication.class, args);
	}
}
