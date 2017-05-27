package Order.Controller;

import Order.DTOs.AccountDTO;
import Order.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controller um Accounts an den Webocket zu senden und Nachrichten
 * von diesem zu Empfangen
 * Created by Martin Petzold on 31.01.2017.
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
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Methode die den Sendepunkt des Websockets
     * definiert
     * @param in
     * @return
     */
    @SendTo("${RESTConfiguration.broadcast.out}" + "${RESTConfiguration.broadcast.endpoint.account.sendPath}")
    public AccountDTO broadcast(AccountDTO in) {
        return in;
    }

    /**
     * methode die den Empfangspunkt des Websockets
     * definiert
     * @param in
     */
    @MessageMapping("${RESTConfiguration.broadcast.endpoint.account.message}")
    public void getBroadcast(AccountDTO in) {
        this.template.convertAndSend(out + sendPath, in);
    }


    /**
     * Sendet das den Account an den Websocket
     * @param in
     */
    public void broadcastAccount(AccountDTO in) {
        System.out.println("Fire");
        getBroadcast(in);
    }
}
