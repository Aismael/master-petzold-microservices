package cookbook.DTOs;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by Aismael on 03.04.2017.
 */
public class AccountInDTO {
    @Column(unique = true)
    private String mail;
    @Column
    private String name;
    @Column
    private String surename;

    public AccountInDTO(String mail, String name, String surename){
        this.mail=mail;
        this.name=name;
        this.surename=surename;

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
