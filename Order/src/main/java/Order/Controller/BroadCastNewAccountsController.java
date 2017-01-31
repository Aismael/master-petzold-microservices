package Order.Controller;

import Order.DTOs.AccountBroadcastDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Aismael on 31.01.2017.
 */
@RestController
@RequestMapping(path = "/br")
public class BroadCastNewAccountsController {
    public AccountBroadcastDto broadcast(AccountBroadcastDto in){
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(in);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        return in;
    }
}
