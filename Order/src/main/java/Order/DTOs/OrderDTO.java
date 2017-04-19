package Order.DTOs;

import Order.Entities.ItemSet;
import Order.Entities.OrderConcepts.Favorite;
import Order.Entities.OrderConcepts.Order;

import java.util.ArrayList;
import java.util.Date;

/**
 * DTO einer Bestellung
 * Created by Martin Petzold on 31.01.2017.
 */
public class OrderDTO {
    private Long id;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", posted=" + posted +
                ", date=" + date +
                ", itemSetStubDTOS=" + itemSetStubDTOS +
                '}';
    }

    private Long accountId;
    private Boolean posted = false;
    private Date date;

    public ArrayList<ItemSetStubDTO> getItemSetStubDTOS() {
        return itemSetStubDTOS;
    }

    public void setItemSetStubDTOS(ArrayList<ItemSetStubDTO> itemSetStubDTOS) {
        this.itemSetStubDTOS = itemSetStubDTOS;
    }

    private ArrayList<ItemSetStubDTO> itemSetStubDTOS = new ArrayList<>();

    public OrderDTO() {
    }

    public OrderDTO(Long accountId, Date date) {
        this.accountId = accountId;
        this.date = date;
    }

    public OrderDTO(Long id, Long accountId, Boolean posted, Date date, ArrayList<ItemSetStubDTO> itemSetStubDTOS) {
        this.id = id;
        this.accountId = accountId;
        this.posted = posted;
        this.date = date;
        this.itemSetStubDTOS = itemSetStubDTOS;
    }

    public OrderDTO(Long id, Long accountId, Boolean posted, Date date) {
        this.id = id;
        this.accountId = accountId;
        this.posted = posted;
        this.date = date;
    }

    public OrderDTO(Order order) {
        this.posted = order.getPosted();
        this.date = order.getDate();
        this.id = order.getId();
        this.accountId = order.getAccount().getOne().getId();
        for (ItemSet itemset : order.getItemSets().getAll()) {
            this.getItemSetStubDTOS().add(new ItemSetStubDTO(itemset));
        }

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

    public Boolean getPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<ItemSetStubDTO> getitemSetStubDtos() {
        return itemSetStubDTOS;
    }

    public void setitemSetStubDtos(ArrayList<ItemSetStubDTO> itemSetStubDTOS) {
        this.itemSetStubDTOS = itemSetStubDTOS;
    }


}
