package Billing.DTOs;

import Billing.Entities.Position;
import Billing.Entities.XOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * DTO einer Bestellung
 * Created by Martin Petzold on 31.01.2017.
 */
public class OrderDTO {
    private Long id;
    private Long accountId;
    private Date date;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    private BigDecimal sum;
    private ArrayList<ItemSetStubDTO> itemSetStubDTOS = new ArrayList<>();
    private boolean payed;
    public OrderDTO() {
    }

    public OrderDTO(Long accountId, Date date) {
        this.accountId = accountId;
        this.date = date;
    }

    public OrderDTO(Long id, Long accountId, Boolean posted, Date date, ArrayList<ItemSetStubDTO> itemSetStubDTOS) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
        this.itemSetStubDTOS = itemSetStubDTOS;
    }

    public OrderDTO(Long id, Long accountId, Boolean posted, Date date) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
    }

    public OrderDTO(XOrder order) {
        this.date = order.getSendDate();
        this.id = order.getId();
        this.accountId = order.getAccount().getOne().getId();
        for (Position position : order.getPositions().getAll()) {
            this.getItemSetStubDTOS().add(new ItemSetStubDTO(position));
        }
        this.payed=order.isPayed();
        this.sum=order.getSum();

    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", date=" + date +
                ", itemSetStubDTOS=" + itemSetStubDTOS +
                '}';
    }

    public ArrayList<ItemSetStubDTO> getItemSetStubDTOS() {
        return itemSetStubDTOS;
    }

    public void setItemSetStubDTOS(ArrayList<ItemSetStubDTO> itemSetStubDTOS) {
        this.itemSetStubDTOS = itemSetStubDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



}
