package Billing.DTOs;

/**
 * Created by Aismael on 31.01.2017.
 */
public class AccountBroadcastDto {
    Long id=null;
    String mail="";

    public AccountBroadcastDto(Long id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public AccountBroadcastDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "AccountBroadcastDto{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                '}';
    }
}
