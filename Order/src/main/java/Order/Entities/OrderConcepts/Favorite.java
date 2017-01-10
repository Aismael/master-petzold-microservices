package Order.Entities.OrderConcepts;

import Order.Entities.Account;
import Order.Entities.OrderConcept;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToOne;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Favorite extends OrderConcept {

    public void setAccount(Account account) {
        this.account = account;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="itemFavorite_id")
    private Account account;
    @Transient
    private ToOne<Favorite, Account> toAccount = new ToOne<>(
            () -> account, (Account a) -> account = a,
            this, Account::getFavorites);

    public IToAny<Account> getAccount() {
        return toAccount;
    }
    private String name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;
}
