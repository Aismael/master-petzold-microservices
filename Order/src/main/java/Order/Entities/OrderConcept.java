package Order.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Elternklasse für Bestellung und Favorit
 */
@Entity
@JsonSerialize
@Inheritance
public abstract class OrderConcept {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    @OneToMany(mappedBy = "orderConcept", cascade = CascadeType.ALL)
    private Set<ItemSet> itemSets = new HashSet<>();
    @Transient
    private ToMany<OrderConcept, ItemSet> toItemSets =
            new ToMany<>(() -> itemSets, this, ItemSet::getOrderConcept);

    public IToAny<ItemSet> getItemSets() {
        return toItemSets;
    }

    public void setItemSets(Set<ItemSet> itemSets) {
        this.itemSets = itemSets;
    }

    public BigDecimal getCurrency() {
        BigDecimal c = new BigDecimal("0.0");
        for (ItemSet itemSet : getItemSets().getAll()) {
            BigDecimal temp = itemSet.getItem().getOne().getCurrency().multiply(new BigDecimal(itemSet.getCount()));
            c = c.add(temp);
        }
        return c;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
