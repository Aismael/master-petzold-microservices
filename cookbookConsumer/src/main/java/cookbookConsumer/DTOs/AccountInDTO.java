package cookbookConsumer.DTOs;

/**
 * Created by Aismael on 04.04.2017.
 */
public class AccountInDTO {
    private String mail;
    private String name;
    private String surename;

    public AccountInDTO(String mail, String name, String surename) {
        this.mail = mail;
        this.name = name;
        this.surename = surename;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getSurename() {
        return surename;
    }
}
