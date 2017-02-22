package Billing.DTOs;

/**
 * Created by Aismael on 21.02.2017.
 */
public class CallIdsDto {
    Long accountId,orderId;

    @Override
    public String toString() {
        return "CallIdsDto{" +
                "accountId=" + accountId +
                ", orderId=" + orderId +
                '}';
    }

    public CallIdsDto() {
    }

    public CallIdsDto(Long accountId, Long orderId) {
        this.accountId = accountId;
        this.orderId = orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
