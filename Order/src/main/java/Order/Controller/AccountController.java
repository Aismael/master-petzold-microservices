package Order.Controller;

import Exeptions.AccountWithThisMailNotFoundException;
import Exeptions.AccountWithThisNameNotFoundException;
import Exeptions.MailAllreadyInUseException;
import Exeptions.NameAllreadyInUseException;
import Order.Entities.Account;
import Order.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Aismael on 13.12.2016.
 */

@RestController
@RequestMapping(path = "${RESTConfiguration.view.account.path}")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;


    @PostConstruct
    public void initializeBean() {
        System.out.println("_________________");
        System.out.println("_________________");

    }


    @RequestMapping(value = "${RESTConfiguration.view.account.all.path}")
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
    @RequestMapping(value = "${RESTConfiguration.view.account.one.path}", method = RequestMethod.POST)
    public Account makeAccount(@RequestBody Account account) {
        validateMailNotInUse(account.getMail());
        validateNameNotInUse(account.getName());
        return accountRepository.saveAndFlush(account);
    }


    @RequestMapping(value = "${RESTConfiguration.view.account.one.path}"
            + "${RESTConfiguration.view.account.one.mail.path}" + "/{mail}", method = RequestMethod.GET)
    public Account getAccountByMail(@PathVariable String mail) {
        validateAccountByMail(mail);
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(mail);
        System.out.println(accountRepository.findByMail(mail));
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;");
        return accountRepository.findByMail(mail);
    }

    @RequestMapping(value = "${RESTConfiguration.view.account.one.path}"
            + "${RESTConfiguration.view.account.one.name.path}" + "/{name:.*}", method = RequestMethod.GET)
    public Account getAccountByName(@PathVariable String name) {
        validateAccountByName(name);
        return accountRepository.findByName(name);
    }

    private void validateMailNotInUse(String mail) {
        if (this.accountRepository.findByMailIs(mail).isPresent()) {
            throw new MailAllreadyInUseException(mail);
        }
    }

    private void validateNameNotInUse(String name) {
        if (this.accountRepository.findByNameIs(name).isPresent()) {
            throw new NameAllreadyInUseException(name);
        }
    }

    private void validateAccountByMail(String mail) {
        this.accountRepository.findByMailIs(mail).orElseThrow(
                () -> new AccountWithThisMailNotFoundException(mail));
    }

    private void validateAccountByName(String name) {
        this.accountRepository.findByNameIs(name).orElseThrow(
                () -> new AccountWithThisNameNotFoundException(name));
    }
}

