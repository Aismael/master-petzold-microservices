package Order.DTOs;

import Order.Entities.ItemSet;

/**
 * * Dto der Einzelnen Bestellzeilen als DTO fasst itemset und items zusammen
 * Created by Martin Petzold on 31.01.2017.
 */
public class ItemSetStubDTO {
    Integer count;
    Long itemID;
    String name;

    public ItemSetStubDTO() {
    }

    public ItemSetStubDTO(Integer count, Long itemID, String name) {
        this.count = count;
        this.itemID = itemID;
        this.name = name;
    }

    public ItemSetStubDTO(ItemSet itemSet) {
        this.count = itemSet.getCount();
        this.itemID = itemSet.getItem().getOne().getId();
        this.name = itemSet.getItem().getOne().getName();
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