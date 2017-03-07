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

/**
 * Favorite für vorkonfigurierte Bestellungen
 */
@Entity
public class Favorite extends OrderConcept {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "itemFavorite_id")
    private Account account;
    @Transient
    private ToOne<Favorite, Account> toAccount = new ToOne<>(
            () -> account, (Account a) -> account = a,
            this, Account::getFavorites);
    private String name;
    private int count;

    public Favorite() {
    }

    public Favorite(Account account, String name, int count) {
        this.account = account;
        this.name = name;
        this.count = count;
    }

    public IToAny<Account> getAccount() {
        return toAccount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
