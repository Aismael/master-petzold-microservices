package Billing.DTOs;

import Billing.Entities.Bank;

/**
 * Created by Aismael on 20.04.2017.
 */
public class BankDTO {
    private Long id;
    private String name;
    public BankDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public BankDTO(Bank bank) {
        this.id = bank.getId();
        this.name = bank.getName();
    }

    public BankDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BankDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
