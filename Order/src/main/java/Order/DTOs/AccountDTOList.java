package Order.DTOs;

import Order.Entities.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 16.04.2017.
 */
public class AccountDTOList {
    ArrayList<AccountDTO> list = new ArrayList<>();

    public AccountDTOList(List<Account> accountList) {
        this.setAccountList(accountList);
    }

    public ArrayList<AccountDTO> getList() {
        return list;
    }

    public void setAccountList(List<Account> accountList) {
        for (Account account : accountList) {
            list.add(new AccountDTO(account));
        }
    }
}
