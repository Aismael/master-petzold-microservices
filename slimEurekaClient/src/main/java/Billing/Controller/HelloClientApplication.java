package Billing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spencer Gibb
 */
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class HelloClientApplication {
	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	@RequestMapping("/test")
	public boolean init() {



		return true;
	}
}




