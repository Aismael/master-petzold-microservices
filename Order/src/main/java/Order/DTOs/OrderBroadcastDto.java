package Order.DTOs;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aisma on 13.02.2017.
 */
public class OrderBroadcastDto extends OrderDto {
    @Override
    public String toString() {
        return "OrderBroadcastDto{" +
                "id=" + getId() +
                ", accountId=" + getAccountId() +
                ", posted=" + getPosted() +
                ", date=" + getDate() +
                "itemSetStubBroadcastDto=" + itemSetStubBroadcastDto.toString() +
                '}';
    }

    public ArrayList<ItemSetStubBroadcastDto> getItemSetStubBroadcastDto() {
        return itemSetStubBroadcastDto;
    }

    private  ArrayList<ItemSetStubBroadcastDto> itemSetStubBroadcastDto;

    public OrderBroadcastDto(Long id, Long accountId, Boolean posted, Date date, ArrayList<ItemSetStubBroadcastDto> itemSetStubBroadcastDto) {
        super(id,accountId,posted,date);
        this.itemSetStubBroadcastDto=itemSetStubBroadcastDto;
    }
}
