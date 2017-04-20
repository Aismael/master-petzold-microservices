package Billing.DTOs;

import Billing.Entities.Account;
import Billing.Entities.BankAccount;

import java.math.BigDecimal;

/**
 * DTO um die Accountdaten die vom Websocket des Order Services gesendet werden zu parsen
 * Created by Martin Petzold on 31.01.2017.
 */
public class BankAccountDTO {
    Long id = null;
   BigDecimal ammount;
   Long accountId;
    Long bankID;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    String bankname;

    public BankAccountDTO(Long id, BigDecimal ammount, Long accountId, Long bankID) {
        this.id = id;
        this.ammount = ammount;
        this.accountId = accountId;
        this.bankID = bankID;
    }

    /**
     * leerer Konstruktor
     */
    public BankAccountDTO() {
    }

    public BankAccountDTO(BankAccount bankAccount) {
        this.id = bankAccount.getId();
        this.ammount = bankAccount.getAmmount();
        this.accountId = bankAccount.getAccount().getOne().getId();
        this.bankID = bankAccount.getBank().getOne().getId();
        bankname= bankAccount.getBank().getOne().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "id=" + id +
                ", ammount=" + ammount +
                ", accountId=" + accountId +
                ", bankID=" + bankID +
                '}';
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getBankID() {
        return bankID;
    }

    public void setBankID(Long bankID) {
        this.bankID = bankID;
    }
}
