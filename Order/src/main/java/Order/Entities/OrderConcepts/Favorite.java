package Order.Entities.OrderConcepts;

import Order.Entities.Account;
import Order.Entities.OrderConcept;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Favorite extends OrderConcept {
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="itemFavorite_id")
    private Account account;

    private String name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;
}
