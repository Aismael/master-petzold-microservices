package Order.Entities;

import Order.Entities.OrderConcepts.Favorite;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.geobe.util.association.IToAny;
import de.geobe.util.association.ToOne;

import javax.persistence.*;

/**
 * Created by Aismael on 13.12.2016.
 */
@Entity
public class ItemSet{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique=true)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="item_id")
    private Item item;
    @Transient
    private ToOne<ItemSet,Item> toItem = new ToOne<>(
            () -> item, (Item i) -> item = i,
            this, Item::getItemSets);

    public IToAny<Item> getItem() {
        return toItem;
    }

    private Integer count=1;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="orderConcept_id")
    private OrderConcept orderConcept;
    @Transient
    private ToOne<ItemSet,OrderConcept> toOrderConcept = new ToOne<>(
            () -> orderConcept, (OrderConcept o) -> orderConcept = o,
            this, OrderConcept::getItemSets);

    public IToAny<OrderConcept> getOrderConcept() {
        return toOrderConcept;
    }


    public void setOrderConcept(OrderConcept orderConcept) {
        this.orderConcept = orderConcept;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setItem(Item item) {
        this.item = item;
    }

}
