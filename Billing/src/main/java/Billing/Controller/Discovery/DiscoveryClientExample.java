package Billing.Controller.Discovery;

import Billing.DTOs.AccountBroadcastDto;
import Billing.DTOs.WebSocketConfigDto;
import Billing.DTOs.WebSocketEndpointDto;
import Billing.Loader.DataLoader;
import Billing.Loader.WebSocketDataLoader;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//TODO commit in
@Component
public class DiscoveryClientExample implements CommandLineRunner{
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    WebSocketDataLoader webSocketDataLoader;
    @Autowired
    DataLoader dataLoader;
    @Override
    public void run(String... strings) throws Exception {
        discoveryClient.getInstances("Order").forEach((ServiceInstance s) -> {
            WebSocketConfigDto webSocketConfigDto=new WebSocketConfigDto();
            try {
                webSocketConfigDto=webSocketDataLoader.getFromJSONUrL(new URL(s.getUri()+"/config/json"));
                System.out.println(webSocketConfigDto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dataLoader.getFromJSONUrL(new URL(s.getUri()+"/config/json"),new URL(s.getUri()+""));
                System.out.println(webSocketConfigDto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            WebSocketConfigDto finalWebSocketConfigDto = webSocketConfigDto;
            webSocketConfigDto.getEndpointDtoMap().forEach(
                    ((WebSocketEndpointDto it )-> {
                        String send= finalWebSocketConfigDto.getIn()+it.getMessage();
                        String subscribe= finalWebSocketConfigDto.getOut()+it.getSendPath();
                        //TODO make code more generic
                        if(it.getName().equals("account")) {
                            StompSessionHandler sessionHandler = new DtoOutOfJsonSessionHandler(send, subscribe, AccountBroadcastDto.class, payload -> {
                                //todo  make to obj
                                System.out.println(payload);
                                return true;
                            },payload -> new AccountBroadcastDto());

                            generateStompClientFromWebSocketClient(s, sessionHandler, finalWebSocketConfigDto.getName());
                        }

                        if(it.getName().equals("order")) {
                            StompSessionHandler sessionHandler = new DtoOutOfJsonSessionHandler(send, subscribe, LinkedHashMap.class, payload -> {
                                //todo  make to obj
                                System.out.println(payload);
                                return true;
                            },payload -> new LinkedHashMap<>());

                            generateStompClientFromWebSocketClient(s, sessionHandler, finalWebSocketConfigDto.getName());
                        }

                    })
            );

        });
    }

    public WebSocketClient getInitiatedWebSocketClient(){
        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketClient webSocketClient = sockJsClient;
        return webSocketClient;
    }

    public void generateStompClientFromWebSocketClient(ServiceInstance s, StompSessionHandler sessionHandler, String websocketname){
        WebSocketStompClient stompClient = new WebSocketStompClient(getInitiatedWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        stompClient.connect(s.getUri()+websocketname, sessionHandler);
    }
}
