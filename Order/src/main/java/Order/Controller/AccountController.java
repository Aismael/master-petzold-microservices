package Order.Controller;

import Order.Entities.Account;
import Order.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Aismael on 13.12.2016.
 */
@RestController
@RequestMapping("/Account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @RequestMapping(method = RequestMethod.GET)
    public List<Account> findItems() {
        return accountRepository.findAll();
    }
}
