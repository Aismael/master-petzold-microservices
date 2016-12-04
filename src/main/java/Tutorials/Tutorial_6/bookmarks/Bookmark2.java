package Tutorials.Tutorial_6.bookmarks;

/**
 * Created by Aismael on 04.12.2016.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bookmark2 {

    @JsonIgnore
    @ManyToOne
    private Account2 account;

    @Id
    @GeneratedValue
    private Long id;

    public Bookmark2() { // jpa only
    }

    public Bookmark2(Account2 account, String uri, String description) {
        this.uri = uri;
        this.description = description;
        this.account = account;
    }

    public String uri;
    public String description;

    public Account2 getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }
}
