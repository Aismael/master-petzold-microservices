package cookbook.DTOs;

import cookbook.Entities.Account;

/**
 * Created by Aismael on 03.04.2017.
 */
public class AccountOutDTO {
    Long id;
    String mail;
    String completeName;

    public AccountOutDTO(Account account){
        id=account.getId();
        mail=account.getMail();
        completeName=account.getSurename()+","+account.getName();
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getCompleteName() {
        return completeName;
    }
}
