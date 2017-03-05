package Billing.Controller.Discovery;

import Billing.DTOs.AccountBroadcastDto;
import Billing.DTOs.WebSocketConfigDto;
import Billing.DTOs.WebSocketEndpointDto;
import Billing.Entities.Account;
import Billing.Entities.Position;
import Billing.Entities.XOrder;
import Billing.Loader.DataLoader;
import Billing.Loader.WebSocketDataLoader;
import Billing.Repositories.AccountRepository;
import Billing.Repositories.XOrderRepository;
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

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

//TODO commit in
@Component
public class DiscoveryClientExample implements CommandLineRunner{
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    WebSocketDataLoader webSocketDataLoader;
    @Autowired
    DataLoader dataLoader;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    XOrderRepository orderRepository;
    //TODO own Exception for not Reachable Eureka Service
    @Override
    public void run(String... strings) throws Exception {
        while(discoveryClient.getInstances("Order").isEmpty()){
            System.err.println("WaitFor:ORDER");
            TimeUnit.SECONDS.sleep(10);
        }
        discoveryClient.getInstances("Order").forEach((ServiceInstance s) -> {
           loadDataFromOrder(s);
        });
        System.out.println("******************************");


        System.out.println("******************************");
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

    public void loadDataFromOrder(ServiceInstance s){
        WebSocketConfigDto webSocketConfigDto = new WebSocketConfigDto();
        try {
            webSocketConfigDto = webSocketDataLoader.getFromJSONUrL(new URL(s.getUri() + "/config/json"));
            System.out.println(webSocketConfigDto);
        } catch (Exception e) {
            System.err.println(s.getUri() + "/config/json" + ":" + "NotReachable");
            e.printStackTrace();
        }
        try {
            dataLoader.getFromJSONUrL(new URL(s.getUri() + "/config/json"), new URL(s.getUri() + ""));
            System.out.println(webSocketConfigDto);
        } catch (Exception e) {
            System.err.println(s.getUri() + "/config/json" + ":" + "NotReachable");
            e.printStackTrace();
        }
        if (webSocketConfigDto != null) {

            WebSocketConfigDto finalWebSocketConfigDto = webSocketConfigDto;
            webSocketConfigDto.getEndpointDtoMap().forEach(
                    ((WebSocketEndpointDto it) -> {
                        String send = finalWebSocketConfigDto.getIn() + it.getMessage();
                        String subscribe = finalWebSocketConfigDto.getOut() + it.getSendPath();
                        //TODO make code more generic
                        if (it.getName().equals("account")) {
                            StompSessionHandler sessionHandler = new DtoOutOfJsonSessionHandler(send, subscribe, AccountBroadcastDto.class, payload -> {
                                //TODO make to obj
                                System.out.println("####account####");
                                System.out.println(payload);
                                Account account = new Account();
                                try {
                                    account.setId(((AccountBroadcastDto) payload).getId());
                                    account.setMail(((AccountBroadcastDto) payload).getMail());
                                    accountRepository.saveAndFlush(account);
                                } catch (Exception e) {
                                    System.err.println("Account:" + "Incomplete");
                                    //e.printStackTrace();
                                }
                                return true;
                            }, payload -> new AccountBroadcastDto());
                            generateStompClientFromWebSocketClient(s, sessionHandler, finalWebSocketConfigDto.getName());
                        }
                        if (it.getName().equals("order")) {
                            StompSessionHandler sessionHandler = new DtoOutOfJsonSessionHandler(send, subscribe, LinkedHashMap.class, payload -> {
                                //TODO  make to obj
                                System.out.println("####order####");
                                System.out.println(payload);
                                XOrder order = new XOrder();
                                try {
                                    order.setId(((Integer) ((LinkedHashMap) payload).get("id")).longValue());
                                    order.setSendDate(new Date((Long) ((LinkedHashMap) payload).get("date")));
                                    order.getAccount().add(accountRepository.getOne(((Integer) ((LinkedHashMap) payload).get("accountId")).longValue()));
                                    ((List) ((LinkedHashMap) payload).get("itemSetStubBroadcastDto")).forEach((Object iter) -> {
                                        Position position = new Position();
                                        position.setName(((LinkedHashMap) iter).get("name").toString());
                                        position.setAmmount(new BigDecimal(((LinkedHashMap) iter).get("ammount").toString()));
                                        position.setCount((int) ((LinkedHashMap) iter).get("count"));
                                        order.getPositions().add(position);
                                    });
                                    orderRepository.flush();
                                    orderRepository.saveAndFlush(order);
                                } catch (Exception e) {
                                    System.err.println("order:" + "Incomplete");
                                    //e.printStackTrace();
                                }

                                return true;
                            }, payload -> new LinkedHashMap<>());
                            generateStompClientFromWebSocketClient(s, sessionHandler, finalWebSocketConfigDto.getName());
                        }
                    })
            );
        }
    }
}
