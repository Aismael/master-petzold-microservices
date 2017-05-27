package Billing.DTOs;

/**
 * DTO mit dem Daten um eine Order zu bezahlen
 * Created by Martin Petzold on 24.02.2017.
 */
public class PayDTO {
    Long bankAccountId, orderId;

    public PayDTO() {
    }

    /**
     * @param bankAccountId
     * @param orderId
     */
    public PayDTO(Long bankAccountId, Long orderId) {
        bankAccountId = bankAccountId;
        orderId = orderId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
