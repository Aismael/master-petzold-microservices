package Order.Controller;

import Exeptions.AccountWithThisMailNotFoundException;
import Exeptions.AccountWithThisNameNotFoundException;
import Exeptions.MailAllreadyInUseException;
import Exeptions.NameAllreadyInUseException;
import Order.DTOs.AccountDTO;
import Order.DTOs.AccountDTOList;
import Order.Entities.Account;
import Order.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Controller für den Nutzeraccount
 * Created by Martin Petzold on 13.12.2016.
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


    /**
     * gibt eine Liste aller Accounts zurück
     * @return die Liste aller Accounts
     */
    @RequestMapping(value = "${RESTConfiguration.view.account.all.path}")
    public AccountDTOList findAccounts() {
        return new AccountDTOList(accountRepository.findAll());
    }

    /**
     * Speichert einen Account
     * @param account
     * @return ein Account
     */
    @RequestMapping(value = "${RESTConfiguration.view.account.one.path}", method = RequestMethod.POST)
    public AccountDTO makeAccount(@RequestBody Account account) {
        validateMailNotInUse(account.getMail());
        validateNameNotInUse(account.getName());
        return new AccountDTO(accountRepository.saveAndFlush(account));
    }


    /**
     * gibt einen Account anhand einer mail zurück
     * @param mail
     * @return ein Account
     */
    @RequestMapping(value = "${RESTConfiguration.view.account.one.path}"
            + "${RESTConfiguration.view.account.one.mail.path}" + "/{mail}", method = RequestMethod.GET)
    public AccountDTO getAccountByMail(@PathVariable String mail) {
        validateAccountByMail(mail);
        return new AccountDTO(accountRepository.findByMail(mail));
    }

    /**
     * gibt einen Account anhand seines namens zurück
     * @param name
     * @return ein Account
     */
    @RequestMapping(value = "${RESTConfiguration.view.account.one.path}"
            + "${RESTConfiguration.view.account.one.name.path}" + "/{name:.*}", method = RequestMethod.GET)
    public AccountDTO getAccountByName(@PathVariable String name) {
        validateAccountByName(name);
        return new AccountDTO(accountRepository.findByName(name));
    }

    /**
     * überprüft ob die Mail schone genutzt wird
     * @param mail der zu überprüfenden Mail
     */
    private void validateMailNotInUse(String mail) {
        if (this.accountRepository.findByMailIs(mail).isPresent()) {
            throw new MailAllreadyInUseException(mail);
        }
    }

    /**
     * überprüft ob der Name schone genutzt wird
     * @param name der zu überprüfenden name
     */
    private void validateNameNotInUse(String name) {
        if (this.accountRepository.findByNameIs(name).isPresent()) {
            throw new NameAllreadyInUseException(name);
        }
    }

    /**
     * überprüft ob zu der mail ein Account vorhanden ist
     * @param mail
     */
    private void validateAccountByMail(String mail) {
        this.accountRepository.findByMailIs(mail).orElseThrow(
                () -> new AccountWithThisMailNotFoundException(mail));
    }

    /**
     * überprüft ob zu dem namen ein Account vorhanden ist
     * @param name
     */
    private void validateAccountByName(String name) {
        this.accountRepository.findByNameIs(name).orElseThrow(
                () -> new AccountWithThisNameNotFoundException(name));
    }
}

