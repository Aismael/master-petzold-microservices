package Billing.DTOs;

/**
 * Created by Aismael on 24.02.2017.
 */
public class MakeNewAccountDTO {
    Long accountId,bankId;

    public MakeNewAccountDTO() {
    }

    public MakeNewAccountDTO(Long accountId, Long bankId) {
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
