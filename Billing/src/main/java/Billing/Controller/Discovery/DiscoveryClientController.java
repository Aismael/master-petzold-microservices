package Billing.Controller.Discovery;

import Billing.DTOs.AccountDto;
import Billing.DTOs.WebSocketConfigDto;
import Billing.DTOs.WebSocketEndpointDto;
import Billing.Entities.Account;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Sucht die Vorhandenen Instanzen des Order Services und Meldet sich bei dessen Websockets an
 */
@Component
public class DiscoveryClientController implements CommandLineRunner {
    @Autowired
    WebSocketDataLoader webSocketDataLoader;
    @Autowired
    DataLoader dataLoader;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    XOrderRepository orderRepository;
    @Autowired
    private DiscoveryClient discoveryClient;
    //TODO own Exception for not Reachable Eureka Service

    /**
     * Sucht die Instanzen des Order Services nach dem Eigenen Servicestart bis min einer gefunden wurde
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        while (discoveryClient.getInstances("Order").isEmpty()) {
            System.err.println("WaitFor:ORDER");
            TimeUnit.SECONDS.sleep(10);
        }
        discoveryClient.getInstances("Order").forEach((ServiceInstance s) -> {
            loadDataFromOrder(s);
        });
    }

    /**
     * Erzeugt einen Neuen Initierten SockJS Websocket Client
     *
     * @return SockJs Websocket Client
     */
    public WebSocketClient getInitiatedWebSocketClient() {
        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketClient webSocketClient = sockJsClient;
        return webSocketClient;
    }

    /**
     * Generiert einen Stomp sockjs websocketclient und verbindet diesen zu einem Server,der Service Instanz
     *
     * @param s              Service Instanz
     * @param sessionHandler Session Handler
     * @param websocketname  Name des Websockets
     */
    public void generateStompClientFromWebSocketClient(ServiceInstance s, StompSessionHandler sessionHandler, String websocketname) {
        WebSocketStompClient stompClient = new WebSocketStompClient(getInitiatedWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        stompClient.connect(s.getUri() + websocketname, sessionHandler);
    }

    /**
     * Lädt die Websocketdaten eines Service
     * Und Verbindet sich mit den Websockets
     * und definiert die Methoden die auf den Broadcast des Websockets Reagieren
     *
     * @param s Service Instanz
     */
    public void loadDataFromOrder(ServiceInstance s) {
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
                        /**
                         *
                         */
                        if (it.getName().equals("account")) {
                            StompSessionHandler sessionHandler = new DtoOutOfJsonSessionHandler(send, subscribe, AccountDto.class, payload -> {
                                Account account = new Account();
                                try {
                                    System.out.println("ACCOUNT OVER SOCKET"+payload);
                                } catch (Exception e) {
                                    System.err.println("Account:" + "Incomplete");
                                    e.printStackTrace();
                                }
                                return true;
                            }, payload -> new AccountDto());
                            generateStompClientFromWebSocketClient(s, sessionHandler, finalWebSocketConfigDto.getName());
                        }
                        /**
                         *
                         */
                        if (it.getName().equals("order")) {
                            StompSessionHandler sessionHandler = new DtoOutOfJsonSessionHandler(send, subscribe, LinkedHashMap.class, payload -> {
                                XOrder order = new XOrder();
                                try {
                                    System.out.println("Order OVER SOCKET"+payload);
                                } catch (Exception e) {
                                    System.err.println("order:" + "Incomplete");
                                    e.printStackTrace();
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
