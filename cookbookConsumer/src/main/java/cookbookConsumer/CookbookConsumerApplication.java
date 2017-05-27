package cookbookConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConfigurationProperties
@EnableConfigurationProperties
@ComponentScan({"cookbookConsumer.Controller"})
//@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class CookbookConsumerApplication {

	@RequestMapping("/")
    public String hello(){
	    return "Hello World";
    }

	public static void main(String[] args) {
		SpringApplication.run(CookbookConsumerApplication.class, args);
	}
}
