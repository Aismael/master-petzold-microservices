package Tutorials.Tutorial_5.bookmarks;

/**
 * Created by Aismael on 04.12.2016.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bookmarkx {

    @JsonIgnore
    @ManyToOne
    private Accountx accountx;

    @Id
    @GeneratedValue
    private Long id;

    public Bookmarkx() { // jpa only
    }

    public Bookmarkx(Accountx accountx, String uri, String description) {
        this.uri = uri;
        this.description = description;
        this.accountx = accountx;
    }

    public String uri;
    public String description;

    public Accountx getAccountx() {
        return accountx;
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
