package Order.DTOs;

import java.util.ArrayList;
import java.util.Date;

/**
 *  * DTO der Bestellung welches über den Websocket geposted wird

 * Created by aisma on 13.02.2017.
 */
public class OrderBroadcastDTO extends OrderDTO {
    @Override
    public String toString() {
        return "OrderBroadcastDTO{" +
                "id=" + getId() +
                ", accountId=" + getAccountId() +
                ", posted=" + getPosted() +
                ", date=" + getDate() +
                "itemSetStubBroadcastDTO=" + itemSetStubBroadcastDTO.toString() +
                '}';
    }

    public OrderBroadcastDTO() {
    }

    public ArrayList<ItemSetStubBroadcastDTO> getItemSetStubBroadcastDTO() {
        return itemSetStubBroadcastDTO;
    }

    private  ArrayList<ItemSetStubBroadcastDTO> itemSetStubBroadcastDTO;

    public OrderBroadcastDTO(Long id, Long accountId, Boolean posted, Date date, ArrayList<ItemSetStubBroadcastDTO> itemSetStubBroadcastDTO) {
        super(id,accountId,posted,date);
        this.itemSetStubBroadcastDTO = itemSetStubBroadcastDTO;
    }
}
