package Billing.DTOs;



import Billing.Entities.Account;
import Billing.Entities.BankAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 19.04.2017.
 */

public class BankAccountDTOList {
    public ArrayList<BankAccountDTO> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(ArrayList<BankAccountDTO> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    ArrayList<BankAccountDTO> bankAccountList = new ArrayList<>();

    @Override
    public String toString() {
        return "AccountDTOList{" +
                "bankAccountList=" + bankAccountList.toString() +
                '}';
    }

    public BankAccountDTOList(List<BankAccount> bankAccountList) {
        this.setBankAccountList(bankAccountList);
    }

    public BankAccountDTOList() {
    }



    public void setBankAccountList(List<BankAccount> accountList){
        for (BankAccount bankAccount : accountList) {
            this.bankAccountList.add(new BankAccountDTO(bankAccount));
        }
    }
}