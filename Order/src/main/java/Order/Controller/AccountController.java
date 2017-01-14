package Order.Controller;

import Exeptions.AccountWithThisMailNotFoundException;
import Exeptions.AccountWithThisNameNotFoundException;
import Exeptions.MailAllreadyInUseException;
import Order.Entities.Account;
import Order.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Account> findAccounts() {
        return accountRepository.findAll();
    }

    /**
     * http://localhost:8080/Account
     * {
     * "name": "Test",
     * "mail": "aismaelinctest@web.de"
     * }
     *
     * @param account
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Account makeAccount(@RequestBody Account account) {
        validateMailNotInUse(account.getMail());
        return accountRepository.saveAndFlush(account);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/mail/{mail:.+}")
    public Account getAccountByMail(@PathVariable String mail) {
        validateAccountByMail(mail);
        return accountRepository.findByMail(mail);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
    public Account getAccountByName(@PathVariable String name) {
        validateAccountByName(name);
        return accountRepository.findByName(name);
    }

    private void validateMailNotInUse(String mail) {
        if (this.accountRepository.findByMailIs(mail).isPresent()) {
            throw new MailAllreadyInUseException(mail);
        }
    }

    private void validateAccountByMail(String mail) {
        this.accountRepository.findByMailIs(mail).orElseThrow(
                () -> new AccountWithThisMailNotFoundException(mail));
    }

    private void validateAccountByName(String name) {
        this.accountRepository.findByMailIs(name).orElseThrow(
                () -> new AccountWithThisNameNotFoundException(name));
    }
}

