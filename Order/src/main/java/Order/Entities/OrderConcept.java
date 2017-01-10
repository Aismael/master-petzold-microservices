package Order.Entities;

import Order.Entities.OrderConcepts.Order;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToMany;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance
public abstract class OrderConcept {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique=true)
    private Long id;

    @OneToMany(mappedBy = "orderConcept",cascade = CascadeType.ALL)
    private Set<ItemSet> itemSets=new HashSet<>();
    @Transient
    private ToMany<OrderConcept,ItemSet> toItemSets=
            new ToMany<>(()->itemSets, this, ItemSet::getOrderConcept);

    public IToAny<ItemSet> getItemSets() {
        return toItemSets;
    }

    public void setItemSets(Set<ItemSet> itemSets) {
        this.itemSets = itemSets;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }










}
