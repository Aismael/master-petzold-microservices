package Order.DTOs;

import Order.Entities.Account;

/**
 * DTO des Accounts welches über den Websocket geposted wird
 * Created by Martin Petzold on 31.01.2017.
 */
public class AccountDTO {
    Long id=null;
    String name="";
    String mail="";

    public AccountDTO(Long id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.mail = account.getMail();
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
