package cookbook.Entities;

import javax.persistence.*;

/**
 * Created by aisma on 30.03.2017.
 */
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String mail;
    @Column
    private String name;
    @Column
    private String surename;

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public Account() {
    }

    public Account(String mail, String name, String surename, Integer age) {
        this.mail = mail;
        this.name = name;
        this.surename = surename;
        this.age = age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column
    private Integer age;

}
