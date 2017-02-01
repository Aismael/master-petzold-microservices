package Billing.Controller;

import Billing.DTOs.AccountBroadcastDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Aismael on 31.01.2017.
 */
@RestController
@RequestMapping(path = "${RESTConfiguration.broadcast.account.path}")
public class BroadCastNewAccountsController {

    @RequestMapping(method = RequestMethod.GET)
    public AccountBroadcastDto broadcast(@RequestBody AccountBroadcastDto in) {
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(in);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        return in;
    }


}
