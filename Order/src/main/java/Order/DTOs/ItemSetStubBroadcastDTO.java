package Order.DTOs;

import java.math.BigDecimal;

/**
 * Dto der Einzelnen Bestellzeilen als DTO fasst itemset und items zusammen, zum senden zum Websocket
 * Created by Martin Petzold on 31.01.2017.
 */
public class ItemSetStubBroadcastDTO {
    Integer count;
    Long itemID;

    @Override
    public String toString() {
        return "ItemSetStubBroadcastDTO{" +
                "ammount=" + ammount +
                "count=" + count +
                ", itemID=" + itemID +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    BigDecimal ammount;

    public ItemSetStubBroadcastDTO() {
    }

    public ItemSetStubBroadcastDTO(Integer count, Long itemID, String name, BigDecimal ammount) {
        this.count = count;
        this.itemID = itemID;
        this.name= name;
        this.ammount=ammount;
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