package Order.Controller;

import Order.DTOs.AccountBroadcastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Created by Aismael on 31.01.2017.
 */
@Controller
public class BroadCastNewAccountsController {
    Queue<AccountBroadcastDto> accountBroadcastDtos = new LinkedList<>();
    @Autowired
    private SimpMessagingTemplate template;

   @MessageMapping("/accountIn")
   @SendTo("/data/accountOut")
    public AccountBroadcastDto broadcast(AccountBroadcastDto in)  {
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(in);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        return in;
    }

    public boolean set(AccountBroadcastDto abd) {
        return accountBroadcastDtos.add(abd);
    }

    public AccountBroadcastDto get() {
        return accountBroadcastDtos.poll();
    }

    public boolean look() {
        return accountBroadcastDtos.isEmpty();
    }

    public void  broadcastAccount(AccountBroadcastDto in) {
        System.out.println("Fire");
        this.template.convertAndSend("/data/accountOut", in);
    }
}
