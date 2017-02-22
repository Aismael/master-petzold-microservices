package Billing.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToOne;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Aismael on 16.02.2017.
*/
@Entity
@Proxy(lazy = false)
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown=true)
public class Position {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Id
    @GeneratedValue

    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "xorder_id")
    private XOrder xorder;
    @Transient
    private ToOne<Position, XOrder> toXOrder = new ToOne<>(
            () -> xorder, (XOrder i) -> xorder = i,
            this, XOrder::getPositions);
    @JsonIgnore
    public IToAny<XOrder> getXOrder() {
        return toXOrder;

    }
    private BigDecimal ammount;
    private int count=1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


}
