package Order.Entities;

import Order.Entities.OrderConcepts.Favorite;
import Order.Entities.OrderConcepts.Order;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;

@Entity
public class Account {

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();
    @Transient
    private ToMany<Account,Order> toOrders=
            new ToMany<>(()->orders, this, Order::getAccount);

    public IToAny<Order> getOrders() {
        return toOrders;
    }

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private Set<Favorite> favorites = new HashSet<>();
    @Transient
    private ToMany<Account,Favorite> toFavorites=
            new ToMany<>(()->favorites, this, Favorite::getAccount);

    public IToAny<Favorite> getFavorites() {
        return toFavorites;
    }
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String name;
    @Column(unique=true)
    private String mail;


    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
