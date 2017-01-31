package Order.Entities.OrderConcepts;

import Order.Entities.Account;
import Order.Entities.OrderConcept;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToOne;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.Date;
@JsonSerialize
@Entity
public class Order extends OrderConcept {

    @JsonIgnore

    @ManyToOne

    @JoinColumn(name = "itemOrder_id")
    private Account account;
    @Transient
    private ToOne<Order, Account> toAccount = new ToOne<>(
            () -> account, (Account a) -> account = a,
            this, Account::getOrders);
    private Boolean posted = false;
    private Date date;
    public Order(Account account, Date date) {
        this.account = account;
        this.date = date;
    }

    public Order() {
    }

    public IToAny<Account> getAccount() {
        return toAccount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Boolean isPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
