package Tutorials.Tutorial_6.bookmarks;

/**
 * Created by Martin Petzold on 04.12.2016.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bookmarky {

    @JsonIgnore
    @ManyToOne
    private Accounty accounty;

    @Id
    @GeneratedValue
    private Long id;

    public Bookmarky() { // jpa only
    }

    public Bookmarky(Accounty account, String uri, String description) {
        this.uri = uri;
        this.description = description;
        this.accounty = account;
    }

    public String uri;
    public String description;

    public Accounty getAccount() {
        return accounty;
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
