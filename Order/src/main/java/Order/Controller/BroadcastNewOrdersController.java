package Order.Controller;

import Order.DTOs.AccountBroadcastDto;
import Order.DTOs.OrderBroadcastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Aismael on 31.01.2017.
 */
public class BroadcastNewOrdersController {
    Queue<OrderBroadcastDto> orderBroadcastDto = new LinkedList<>();
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/orderIn")
    @SendTo("/data/orderOut")
    public AccountBroadcastDto broadcast(AccountBroadcastDto in)  {
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(in);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        return in;
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
        this.template.convertAndSend("/data/accountOut", in);
    }
}
