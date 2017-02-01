package Billing.DTOs;

import java.util.ArrayList;

/**
 * Created by Aismael on 31.01.2017.
 */
public class FavoriteDto {

    private String name;
    private int count;
    private Long id;
    private Long accountId;
    private ArrayList<ItemSetStubDto> itemSetStubDtos = new ArrayList<>();

    public FavoriteDto() {
    }

    public FavoriteDto(String name, int count, Long id, Long accountId, ArrayList<ItemSetStubDto> itemSetStubDtos) {
        this.name = name;
        this.count = count;
        this.id = id;
        this.accountId = accountId;
        this.itemSetStubDtos = itemSetStubDtos;
    }

    public FavoriteDto(String name, int count, Long accountId) {
        this.name = name;
        this.count = count;
        this.accountId = accountId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<ItemSetStubDto> getItemSetStubDtos() {
        return itemSetStubDtos;
    }

    public void setItemSetStubDtos(ArrayList<ItemSetStubDto> itemSetStubDtos) {
        this.itemSetStubDtos = itemSetStubDtos;
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


}
