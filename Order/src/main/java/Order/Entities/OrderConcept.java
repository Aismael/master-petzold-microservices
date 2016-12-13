package Order.Entities;

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

    public Set<ItemSet> getItemSets() {
        return itemSets;
    }

    public void setItemSets(Set<ItemSet> itemSets) {
        this.itemSets = itemSets;
    }

    @OneToMany(mappedBy = "orderConcept",cascade = CascadeType.ALL)
    private Set<ItemSet> itemSets=new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }










}
