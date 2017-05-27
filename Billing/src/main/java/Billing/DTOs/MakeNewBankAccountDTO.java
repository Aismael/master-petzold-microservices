package Billing.DTOs;

/**
 * DTO zum erstellen eines neuen BankAccounts
 * Created by Martin Petzold on 24.02.2017.
 */
public class MakeNewBankAccountDTO {
    Long accountId, bankId;

    /**
     * leerer Konstruktor
     */
    public MakeNewBankAccountDTO() {
    }

    /**
     * @param accountId
     * @param bankId
     */
    public MakeNewBankAccountDTO(Long accountId, Long bankId) {
        this.accountId = accountId;
        this.bankId = bankId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}
