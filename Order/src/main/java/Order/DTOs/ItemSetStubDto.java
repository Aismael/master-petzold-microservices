package Order.DTOs;

/**
 * Created by Aismael on 31.01.2017.
 */
public class ItemSetStubDto {
    Integer count;
    Long itemID;

    @Override
    public String toString() {
        return "ItemSetStubDto{" +
                "count=" + count +
                ", itemID=" + itemID +
                '}';
    }

    public ItemSetStubDto() {
    }

    public ItemSetStubDto(Integer count, Long itemID) {
        this.count = count;
        this.itemID = itemID;
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