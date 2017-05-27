package Tutorials.Tutorial_5.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Martin Petzold on 04.12.2016.
 */
@Entity
public class Accountx {

    @OneToMany(mappedBy = "accountx")
    private Set<Bookmarkx> bookmarkxes = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    public String password;
    public String username;
    public Set<Bookmarkx> getBookmarkxes() {
        return bookmarkxes;
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



    public Accountx(String name, String password) {
        this.username = name;
        this.password = password;
    }

    Accountx() { // jpa only
    }
}
