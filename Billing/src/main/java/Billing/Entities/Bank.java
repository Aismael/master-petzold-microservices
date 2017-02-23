package Billing.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Aismael on 16.02.2017.
*/
@Entity
@JsonSerialize
@Proxy(lazy = false)

@JsonIgnoreProperties(ignoreUnknown=true)
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "bank",cascade = CascadeType.ALL)
    private Set<BankAccount> bankAccounts=new HashSet<>();

     @Transient
    private ToMany<Bank, BankAccount> toBankAccounts =
            new ToMany<>(() -> bankAccounts, this, BankAccount::getBank);
    @JsonIgnore
    public IToAny<BankAccount> getBankAccounts() {
        return toBankAccounts;
    }


    private int answertime;//new Random().nextInt();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnswertime() {
        return answertime;
    }


}
