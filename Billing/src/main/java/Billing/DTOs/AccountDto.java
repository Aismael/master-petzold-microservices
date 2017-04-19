package Billing.DTOs;

/**
 * DTO um die Accountdaten die vom Websocket des Order Services gesendet werden zu parsen
 * Created by Martin Petzold on 31.01.2017.
 */
public class AccountDto {
    Long id = null;
    String mail = "";

    /**
     * Konstruktor des DTOS
     *
     * @param id   id des Accounts
     * @param mail mail adresse des Accounts
     */
    public AccountDto(Long id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    /**
     * leerer Konstruktor
     */
    public AccountDto() {
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
        return "AccountDto{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                '}';
    }
}
