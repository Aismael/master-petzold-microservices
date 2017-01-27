package Order.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique=true)
    private Long id;
    @Column(unique=true)
    private String name;
    private String details;
    private boolean allergens;
    private BigDecimal currency;


    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Set<ItemSet> itemSets=new HashSet<>();
    @Transient
    private ToMany<Item,ItemSet> toItemSets=
            new ToMany<>(()->itemSets, this, ItemSet::getItem);

    public IToAny<ItemSet> getItemSets() {
        return toItemSets;
    }

    public void setItemSets(Set<ItemSet> itemSets) {
        this.itemSets = itemSets;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public boolean isAllergens() {
        return allergens;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAllergens(boolean allergens) {
        this.allergens = allergens;
    }

    public BigDecimal getCurrency() {
        return currency;
    }

    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }



}
