package Billing.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToOne;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Aismael on 16.02.2017.
*/
@Entity
@JsonSerialize
@Proxy(lazy = false)

@JsonIgnoreProperties(ignoreUnknown=true)
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    private BigDecimal ammount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    @Transient
    private ToOne<BankAccount, Account> toAccount = new ToOne<>(
            () -> account, (Account i) -> account = i,
            this, Account::getBankAccounts);
    @JsonIgnore
    public IToAny<Account> getAccount() {
        return toAccount;

    }
    public void setAccount(Account account) {
        this.account = account;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id")
    private Bank bank;


     @Transient
    private ToOne<BankAccount, Bank> toBank = new ToOne<>(
            () -> bank, (Bank i) -> bank = i,
            this, Bank::getBankAccounts);
    public IToAny<Bank> getBank() {
        return toBank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }






}
