package Billing.DTOs;



import Billing.Entities.Account;
import Billing.Entities.XOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 19.04.2017.
 */

public class AccountDTOList {
    public ArrayList<AccountDTO> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<AccountDTO> accountList) {
        this.accountList = accountList;
    }

    ArrayList<AccountDTO> accountList = new ArrayList<>();

    @Override
    public String toString() {
        return "AccountDTOList{" +
                "accountList=" + accountList.toString() +
                '}';
    }

    public AccountDTOList() {
    }



    public void setAccountList(List<Account> accountList){
        for (Account account : accountList) {
            this.accountList.add(new AccountDTO(account));
        }
    }
}