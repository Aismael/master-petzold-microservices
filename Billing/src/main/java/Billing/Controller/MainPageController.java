package Billing.Controller;

import Billing.DTOs.*;
import Billing.Entities.BankAccount;
import Billing.Entities.XOrder;
import Billing.Exception.payException;
import Billing.Repositories.AccountRepository;
import Billing.Repositories.BankAccountRepository;
import Billing.Repositories.BankRepository;
import Billing.Repositories.XOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Rest Controller für die Abfragen der Angular JS UI für den Bezahl Use Case
 *
 * @author Martin Petzold
 */
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class MainPageController {
    @Autowired
    XOrderRepository orderRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    AccountRepository accountRepository;

    /**
     * gibt eine Order zurück wenn die Order id, und die Zugehörige AccountId zur Order Passen
     *
     * @param id        OrderId
     * @param accountId
     * @return Order Entität
     */
    @RequestMapping(value = "${RESTConfiguration.view.order.path}" +
            "${RESTConfiguration.view.order.one.path}" +
            "${RESTConfiguration.view.order.one.idAndAccount.path}" +
            "/{id}/{accountId}"
    )
    public OrderDTO orderByAccount(@PathVariable("id") Long id,
                                   @PathVariable("accountId") Long accountId) {
        System.out.println("oba");
        return new OrderDTO(orderRepository.findByIdAndAccountId(id, accountId));
    }

    /**
     * Gibt alle Bank Accounts eines Accounts zurück
     *
     * @param accountId id des Accounts
     * @return Liste aller dem Account zugehörigen Bank Accounts
     */
    @RequestMapping(value = "${RESTConfiguration.view.bankAccount.path}" +
            "${RESTConfiguration.view.bankAccount.all.path}" +
            "${RESTConfiguration.view.bankAccount.all.account.path}" + "/{accountId}"
    )
    public BankAccountDTOList bankAccountsByAccountId(@PathVariable("accountId") Long accountId) {
        return new BankAccountDTOList(bankAccountRepository.findAllByAccountId(accountId));
    }

    /**
     * Methode zum erstellen eines Neuen Bank Accounts anhand eines DTOs
     *
     * @param makeNewBankAccountDTO das DTo mit den Daten zum erstellen
     * @return der Neue Bankaccount
     */
    @RequestMapping(value = "${RESTConfiguration.view.bankAccount.path}" +
            "${RESTConfiguration.view.bankAccount.one.path}" +
            "${RESTConfiguration.view.bankAccount.one.account.path}"
            , method = RequestMethod.POST
    )
    public BankAccountDTO makebankAccountsByAccountIdAndBankid(@RequestBody MakeNewBankAccountDTO makeNewBankAccountDTO) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmmount(new BigDecimal("100"));
        bankAccount.getBank().add(bankRepository.getOne(makeNewBankAccountDTO.getBankId()));
        bankAccount.getAccount().add(accountRepository.getOne(makeNewBankAccountDTO.getAccountId()));
        bankAccountRepository.flush();
        return new BankAccountDTO(bankAccountRepository.saveAndFlush(bankAccount));
    }

    /**
     * Methode um eine Order zu bezahlen
     *
     * @param payDTO benötigte Daten
     * @return der Bank Account nach dem Bezahlvorgang
     */
    @RequestMapping(value = "${RESTConfiguration.view.bankAccount.path}" +
            "${RESTConfiguration.view.bankAccount.one.path}" +
            "${RESTConfiguration.view.bankAccount.one.pay.path}"
            , method = RequestMethod.POST
    )
    public BankAccountDTO pay(@RequestBody PayDTO payDTO) {
        XOrder order = orderRepository.getOne(payDTO.getOrderId());
        BankAccount bankAccount = bankAccountRepository.findOne(payDTO.getBankAccountId());
        if (bankAccount.getAmmount().subtract(order.getSum()).longValue() >= 0) {
            bankAccount.setAmmount(bankAccount.getAmmount().subtract(order.getSum()));
        } else {
            throw new payException(bankAccount.toString());
        }
        order.setPayed(true);
        orderRepository.saveAndFlush(order);
        return new BankAccountDTO(bankAccountRepository.saveAndFlush(bankAccount));
    }

    /**
     * Methode zur Ausgabe einer Liste aller Banken im System
     *
     * @return Liste Alller Banken
     */
    @RequestMapping(value = "${RESTConfiguration.view.bank.path}" +
            "${RESTConfiguration.view.bank.all.path}")
    public BankDTOList getAllBanks() {
        return new BankDTOList(bankRepository.findAll());
    }
}





