package Order.Entities.OrderConcepts;

import Order.Entities.Account;
import Order.Entities.OrderConcept;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Order extends OrderConcept {
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="itemOrder_id")
    private Account account;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
}
