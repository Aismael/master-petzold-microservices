package Order.DTOs;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aisma on 13.02.2017.
 */
public class OrderBroadcastDto extends OrderDto {


    public ArrayList<ItemSetStubBroadcastDto> getItemSetStubBroadcastDto() {
        return itemSetStubBroadcastDto;
    }

    private  ArrayList<ItemSetStubBroadcastDto> itemSetStubBroadcastDto;

    public OrderBroadcastDto(Long id, Long accountId, Boolean posted, Date date, ArrayList<ItemSetStubBroadcastDto> itemSetStubBroadcastDto) {
        super(id,accountId,posted,date);
        this.itemSetStubBroadcastDto=itemSetStubBroadcastDto;
    }
}
