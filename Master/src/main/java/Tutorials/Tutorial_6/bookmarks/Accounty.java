package Tutorials.Tutorial_6.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aismael on 04.12.2016.
 */
@Entity
public class Accounty {

    @OneToMany(mappedBy = "accounty")
    private Set<Bookmarky> bookmarks = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    public Set<Bookmarky> getBookmarks() {
        return bookmarks;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String password;
    public String username;

    public Accounty(String name, String password) {
        this.username = name;
        this.password = password;
    }

    Accounty() { // jpa only
    }
}
