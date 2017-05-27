package Billing.DTOs;

import Billing.DTOs.BankDTOList;
import Billing.Entities.Bank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 20.04.2017.
 */
public class BankDTOList {
    public ArrayList<BankDTO> getBankDTOList() {
        return bankList;
    }

    public void setBankDTOList(ArrayList<BankDTO> bankList) {
        this.bankList = bankList;
    }

    ArrayList<BankDTO> bankList = new ArrayList<>();


    @Override
    public String toString() {
        return "BankDTOList{" +
                "bankList=" + bankList +
                '}';
    }

    public BankDTOList() {
    }

    public BankDTOList(List<Bank> bankList) {
        setBankDTOList(bankList);
    }

    public void setBankDTOList(List<Bank> bankList){
        for (Bank bank : bankList) {
            this.bankList.add(new BankDTO(bank));
        }
    }

}
