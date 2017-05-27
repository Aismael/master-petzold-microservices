package Order.Controller;

import Order.DTOs.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controller um Bestellungen an den Webocket zu senden und Nachrichten
 * von diesem zu Empfangen
 * Created by Martin Petzold on 31.01.2017.
 */
@Controller
public class BroadcastNewOrdersController {
    @Value("${RESTConfiguration.broadcast.out}")
    private String out;
    @Value("${RESTConfiguration.broadcast.in}")
    private String in;
    @Value("${RESTConfiguration.broadcast.name}")
    private String name;
    @Value("${RESTConfiguration.broadcast.endpoint.order.message}")
    private String message;
    @Value("${RESTConfiguration.broadcast.endpoint.order.sendPath}")
    private String sendPath;
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Methode die den Sendepunkt des Websockets
     * definiert
     * @param in
     * @return
     */
    @SendTo("${RESTConfiguration.broadcast.out}"+"${RESTConfiguration.broadcast.endpoint.order.sendPath}")
    public OrderDTO broadcast(OrderDTO in)  {
        return in;
    }

    /**
     * methode die den Empfangspunkt des Websockets
     * definiert
     * @param in
     */
    @MessageMapping("${RESTConfiguration.broadcast.endpoint.order.message}")
    public void getBroadcast(OrderDTO in)  {
        this.template.convertAndSend(out+sendPath, in);
    }

    /**
     * Sendet das die Bestellung an den Websocket
     * @param in
     */
    public void  broadcastOrder(OrderDTO in) {
        System.out.println("Fire");
        getBroadcast(in);
    }
}
