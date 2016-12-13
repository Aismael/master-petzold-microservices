package Order.Entities;

import javax.persistence.*;

/**
 * Created by Aismael on 13.12.2016.
 */
@Entity
public class ItemSet{
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique=true)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="orderConcept_id")
    private OrderConcept orderConcept;

    private Integer count=1;


}
