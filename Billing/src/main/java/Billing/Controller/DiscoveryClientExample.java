package Billing.Controller;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
@Component
class DiscoveryClientExample implements CommandLineRunner {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public void run(String... strings) throws Exception {
        discoveryClient.getInstances("Order").forEach((ServiceInstance s) -> {
            System.out.println(ToStringBuilder.reflectionToString(s));
            System.out.println(s.getUri());
            List<Transport> transports = new ArrayList<>(2);
            transports.add(new WebSocketTransport(new StandardWebSocketClient()));
            transports.add(new RestTemplateXhrTransport());
            SockJsClient sockJsClient = new SockJsClient(transports);
            WebSocketClient webSocketClient = sockJsClient;
            WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());
            stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
            String url = s.getUri()+"/orderWebsocket";
            StompSessionHandler sessionHandler = new MySessionHandler();
            stompClient.connect(url, sessionHandler);
        });
    }
}