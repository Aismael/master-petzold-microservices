package Billing.Controller;

import Billing.Loader.WebSocketDataLoader;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

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
	WebSocketDataLoader webSocketDataLoader;
	@RequestMapping("/test")
	public boolean init() {



		return true;
	}
}




