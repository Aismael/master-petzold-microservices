package Order.Repositories;

import Order.Entities.Item;
import Order.Entities.ItemSet;
import Order.Entities.OrderConcept;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface ItemSetRepository extends JpaRepository<ItemSet,Long> {
    ItemSet findByOrderConcept(OrderConcept orderConcept);
}
