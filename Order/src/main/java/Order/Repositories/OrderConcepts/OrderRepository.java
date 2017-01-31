package Order.Repositories.OrderConcepts;

import Order.Entities.OrderConcepts.Favorite;
import Order.Entities.OrderConcepts.Order;
import Order.Repositories.OrderConceptRepository;

import java.util.List;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface OrderRepository extends OrderConceptRepository<Order> {
    List<Order> findAllByAccountId(Long accountId);

}
