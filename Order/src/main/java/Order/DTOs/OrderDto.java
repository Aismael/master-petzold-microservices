package Order.DTOs;

import Order.Entities.Account;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aismael on 31.01.2017.
 */
public class OrderDto {
    private Long id;


    private Long accountId;
    private Boolean posted = false;
    private Date date;
    private ArrayList<ItemSetStubDto> itemSetStubDtos = new ArrayList<>();

    public OrderDto() {
    }

    public OrderDto(Long accountId, Date date) {
        this.accountId = accountId;
        this.date = date;
    }

    public OrderDto(Long id, Long accountId, Boolean posted, Date date, ArrayList<ItemSetStubDto> itemSetStubDtos) {
        this.id = id;
        this.accountId = accountId;
        this.posted = posted;
        this.date = date;

        this.itemSetStubDtos = itemSetStubDtos;
    }

    public OrderDto(Long id, Long accountId, Boolean posted, Date date) {
        this.id = id;
        this.accountId = accountId;
        this.posted = posted;
        this.date = date;
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

    public ArrayList<ItemSetStubDto> getitemSetStubDtos() {
        return itemSetStubDtos;
    }

    public void setitemSetStubDtos(ArrayList<ItemSetStubDto> itemSetStubDtos) {
        this.itemSetStubDtos = itemSetStubDtos;
    }


}
