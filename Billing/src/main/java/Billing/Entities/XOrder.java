package Billing.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;
import de.geobe.util.association.ToOne;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aismael on 16.02.2017.
 */
@Entity
@Proxy(lazy = false)
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class XOrder {
    @Id
    private Long id;

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    private Date sendDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    @Transient
    private ToOne<XOrder, Account> toAccount = new ToOne<>(
            () -> account, (Account i) -> account = i,
            this, Account::getXOrders);
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "xorder",cascade = CascadeType.ALL)
    private Set<Position> positions = new HashSet<>();
    @Transient
    private ToMany<XOrder, Position> toPositions =
            new ToMany<>(() -> positions, this, Position::getXOrder);
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
    @JsonIgnore
    public IToAny<Position> getPositions() {
        return toPositions;
    }
}
