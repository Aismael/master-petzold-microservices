package Order.DTOs;

/**
 * Created by Aismael on 31.01.2017.
 */
public class ItemSetStubBroadcastDto {
    Integer count;
    Long itemID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public ItemSetStubBroadcastDto() {
    }

    public ItemSetStubBroadcastDto(Integer count, Long itemID,String name) {
        this.count = count;
        this.itemID = itemID;
        this.name= name;
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