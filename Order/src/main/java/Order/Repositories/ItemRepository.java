package Order.Repositories;

import Order.Entities.Account;
import Order.Entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findByName(String name);
}
