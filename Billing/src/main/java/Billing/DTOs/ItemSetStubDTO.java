package Billing.DTOs;

import Billing.Entities.Position;

import java.math.BigDecimal;

/**
 * * Dto der Einzelnen Bestellzeilen als DTO fasst itemset und items zusammen
 * Created by Martin Petzold on 31.01.2017.
 */
public class ItemSetStubDTO {
    Integer count;
    Long itemID;
    String name;

    public BigDecimal getCurrency() {
        return currency;
    }

    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }

    BigDecimal currency;

    public ItemSetStubDTO() {
    }

    public ItemSetStubDTO(Integer count, Long itemID, String name) {
        this.count = count;
        this.itemID = itemID;
        this.name = name;
    }

    public ItemSetStubDTO(Position position) {
        this.count = position.getCount();
        this.itemID = position.getId();
        this.name = position.getName();
        this.currency = position.getAmmount();
    }

    public ItemSetStubDTO(Integer count, Long id, String name, BigDecimal currency) {
        this.count = count;
        this.itemID = id;
        this.name = name;
        this.currency=currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ItemSetStubDTO{" +
                "count=" + count +
                ", itemID=" + itemID +
                ", name=" + name +
                '}';
    }

    public Integer getCount() {

        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }


}