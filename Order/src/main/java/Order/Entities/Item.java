package Order.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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


    public Set<ItemSet> getItemSets() {
        return itemSets;
    }

    public void setItemSets(Set<ItemSet> itemSets) {
        this.itemSets = itemSets;
    }

    @OneToMany(mappedBy = "item")
    private Set<ItemSet> itemSets=new HashSet<>();

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




}
