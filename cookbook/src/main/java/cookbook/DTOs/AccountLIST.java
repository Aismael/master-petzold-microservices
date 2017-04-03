package cookbook.DTOs;

import cookbook.Entities.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 03.04.2017.
 */
public class AccountLIST {
    ArrayList<AccountOutDTO> accountOutDTOArrayList = new ArrayList<>();

    public AccountLIST(List<Account> accountList) {
        this.setAccountList(accountList);
    }

    public ArrayList<AccountOutDTO> getAccountOutDTOArrayList() {
        return accountOutDTOArrayList;
    }

    public void setAccountList(List<Account> accountList) {
        for (Account account : accountList) {
            accountOutDTOArrayList.add(new AccountOutDTO(account));
        }
    }
}
