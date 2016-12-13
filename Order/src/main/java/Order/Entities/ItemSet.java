package Order.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    private Integer count=1;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="orderConcept_id")
    private OrderConcept orderConcept;

    public OrderConcept getOrderConcept() {
        return orderConcept;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
