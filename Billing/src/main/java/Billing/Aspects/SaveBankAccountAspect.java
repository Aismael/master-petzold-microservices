package Billing.Aspects;

import Billing.Entities.Account;
import Billing.Entities.Bank;
import Billing.Entities.BankAccount;
import Billing.Repositories.BankAccountRepository;
import Billing.Repositories.BankRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Aspect der Einem BankAccount nach der Speicherung ein Startkapital hinzufügt
 * Created by Martin Petzold on 31.01.2017.
 */
@Component
@Aspect
public class SaveBankAccountAspect {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    @Pointcut("execution(* Billing.Repositories.AccountRepository.saveAndFlush(..))")
    public void accountHasSaved() {
    }

    /**
     * fügt dem Account ein Startkapital hinzu
     *
     * @param joinPoint
     * @param returnValue
     */
    @AfterReturning(pointcut = "accountHasSaved()", returning = "returnValue")
    public void broadcast(JoinPoint joinPoint, Object returnValue) {
        if (returnValue instanceof Account) {
            if (bankRepository.findByName("Standard") != null) {
                otherSteps((Account) returnValue, "Standard");
            }
        }
        if (returnValue instanceof Account) {
            if (bankRepository.findByName("Paypal") != null) {
                otherSteps((Account) returnValue, "Paypal");
            }
        }
    }

    public void otherSteps(Account returnValue, String name) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmmount(new BigDecimal("100"));
        bankAccount.getAccount().add((Account) returnValue);
        Bank bank = bankRepository.findByName(name);
        bank.getBankAccounts().add(bankAccount);
        //bankAccountRepository.saveAndFlush(bankAccount);
        bankRepository.saveAndFlush(bank);
    }

}
