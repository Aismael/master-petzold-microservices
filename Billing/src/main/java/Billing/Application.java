package Billing;

import Billing.Controller.MySessionHandler;
import Billing.Loader.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Configuration
@SpringBootApplication
@RestController
@ComponentScan({"Billing","Billing.Aspects","Billing.Loader"})
@EnableJpaRepositories(basePackages = {"Billing.Repositories"})
@EntityScan(basePackages = {"Billing.Entities", "Billing.Beans"})
@EnableScheduling
@EnableAutoConfiguration
@ConfigurationProperties
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients

public class Application {
    @Autowired
    DiscoveryClient client;

    @RequestMapping("/greeting")
    public String home() {
        return "Hello Docker World Iam The Billing Service";
    }

    @RequestMapping("/infoGreeting")
    public String eureka() {
        ServiceInstance localInstance = client.getLocalServiceInstance();
        return "Hello Docker World over nameservice eureka: "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
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