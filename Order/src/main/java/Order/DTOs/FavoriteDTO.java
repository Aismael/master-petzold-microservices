package Order.DTOs;

import java.util.ArrayList;

/**
 * Dto einer Favoriten Liste
 * Created by Martin Petzold on 31.01.2017.
 */
public class FavoriteDTO {

    private String name;
    private int count;
    private Long id;
    private Long accountId;
    private ArrayList<ItemSetStubDTO> itemSetStubDTOS = new ArrayList<>();

    public FavoriteDTO() {
    }

    public FavoriteDTO(String name, int count, Long id, Long accountId, ArrayList<ItemSetStubDTO> itemSetStubDTOS) {
        this.name = name;
        this.count = count;
        this.id = id;
        this.accountId = accountId;
        this.itemSetStubDTOS = itemSetStubDTOS;
    }

    public FavoriteDTO(String name, int count, Long accountId) {
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

    public ArrayList<ItemSetStubDTO> getItemSetStubDTOS() {
        return itemSetStubDTOS;
    }

    public void setItemSetStubDTOS(ArrayList<ItemSetStubDTO> itemSetStubDTOS) {
        this.itemSetStubDTOS = itemSetStubDTOS;
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
