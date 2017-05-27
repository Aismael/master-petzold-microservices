package Billing.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Ein User Account
 * Created by Martin Petzold on 16.02.2017.
 */
@Entity
@Proxy(lazy = false)
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    public Account() {
    }

    public Account(Long id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Id

    private Long id;
    @Column(unique = true)
    private String mail;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<BankAccount> bankAccounts = new HashSet<>();
    @Transient
    private ToMany<Account, BankAccount> toBankAccounts =
            new ToMany<>(() -> bankAccounts, this, BankAccount::getAccount);
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<XOrder> xorders = new HashSet<>();
    ;
    @Transient
    private ToMany<Account, XOrder> toXOrders =
            new ToMany<>(() -> xorders, this, XOrder::getAccount);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @JsonIgnore
    public IToAny<BankAccount> getBankAccounts() {
        return toBankAccounts;
    }

    @JsonIgnore
    public IToAny<XOrder> getXOrders() {
        return toXOrders;
    }
}
