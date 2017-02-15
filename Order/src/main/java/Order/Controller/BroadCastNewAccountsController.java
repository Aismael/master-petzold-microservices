package Order.Controller;

import Order.DTOs.AccountBroadcastDto;
import Order.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${RESTConfiguration.broadcast.out}")
    private String out;
    @Value("${RESTConfiguration.broadcast.in}")
    private String in;
    @Value("${RESTConfiguration.broadcast.name}")
    private String name;
    @Value("${RESTConfiguration.broadcast.endpoint.account.message}")
    private String message;
    @Value("${RESTConfiguration.broadcast.endpoint.account.sendPath}")
    private String sendPath;

    Queue<AccountBroadcastDto> accountBroadcastDtos = new LinkedList<>();
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private AccountRepository accountRepository;

   @SendTo("${RESTConfiguration.broadcast.out}"+"${RESTConfiguration.broadcast.endpoint.account.sendPath}")
   public AccountBroadcastDto broadcast(AccountBroadcastDto in)  {
        return in;
    }

    @MessageMapping("${RESTConfiguration.broadcast.endpoint.account.message}")
    public void getBroadcast(AccountBroadcastDto in)  {
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(in);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        this.template.convertAndSend(out+sendPath, in);
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
        getBroadcast(in);
    }
}
