package Order.Controller;

import Order.DTOs.AccountBroadcastDto;
import Order.DTOs.OrderBroadcastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Aismael on 31.01.2017.
 */
@Controller
public class BroadcastNewOrdersController {
    Queue<OrderBroadcastDto> orderBroadcastDto = new LinkedList<>();
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

    @SendTo("${RESTConfiguration.broadcast.out}"+"${RESTConfiguration.broadcast.endpoint.order.sendPath}")
    public OrderBroadcastDto broadcast(OrderBroadcastDto in)  {
        return in;
    }

    @MessageMapping("${RESTConfiguration.broadcast.endpoint.order.message}")
    public void getBroadcast(OrderBroadcastDto in)  {
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(in);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        this.template.convertAndSend(out+sendPath, in);
    }

    public boolean set(OrderBroadcastDto abd) {
        return orderBroadcastDto.add(abd);
    }

    public OrderBroadcastDto get() {
        return orderBroadcastDto.poll();
    }

    public boolean look() {
        return orderBroadcastDto.isEmpty();
    }

    public void  broadcastOrder(OrderBroadcastDto in) {
        System.out.println("Fire");
        getBroadcast(in);
    }
}
