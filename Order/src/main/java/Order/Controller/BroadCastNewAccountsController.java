package Order.Controller;

import Order.DTOs.AccountBroadcastDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by Aismael on 31.01.2017.
 */
@Controller
public class BroadCastNewAccountsController {
    Queue<AccountBroadcastDto> accountBroadcastDtos = new LinkedList<>();


    @Async
    public void broadcastRunner(){
        if(!look()){
            broadcast(get());
        }else{
            System.out.println("idle");
        }
    }
   @MessageMapping("/hello")
   @SendTo("/topic/greetings")
    public String broadcast(AccountBroadcastDto in) {
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(in);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        return "test";
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
}
