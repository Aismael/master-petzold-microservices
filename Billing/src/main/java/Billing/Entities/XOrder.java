package Billing.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;
import de.geobe.util.association.ToOne;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * eine Bestellung
 * Created by Martin Petzold on 16.02.2017.
 */
@Entity
@Proxy(lazy = false)
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class XOrder {
    @Id
    private Long id;
    private Date sendDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    @Transient
    private ToOne<XOrder, Account> toAccount = new ToOne<>(
            () -> account, (Account i) -> account = i,
            this, Account::getXOrders);
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "xorder", cascade = CascadeType.ALL)
    private Set<Position> positions = new HashSet<>();
    @Transient
    private ToMany<XOrder, Position> toPositions =
            new ToMany<>(() -> positions, this, Position::getXOrder);

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "XOrder{" +
                "id=" + id +
                ", sendDate=" + sendDate +
                ", account=" + account +
                ", toAccount=" + toAccount +
                ", positions=" + positions +
                ", toPositions=" + toPositions.getAll() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public IToAny<Account> getAccount() {
        return toAccount;
    }

    public IToAny<Position> getPositions() {
        return toPositions;
    }

    public BigDecimal getSum() {
        final BigDecimal[] bigDecimal = {new BigDecimal("0")};
        getPositions().getAll().forEach(
                (Position p) -> bigDecimal[0] = bigDecimal[0].add(p.getAmmount().multiply(new BigDecimal(p.getCount())))
        );
        return bigDecimal[0];
    }
}
