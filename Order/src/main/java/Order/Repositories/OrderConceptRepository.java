package Order.Repositories;

import Order.Entities.Account;
import Order.Entities.OrderConcept;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface OrderConceptRepository<O extends OrderConcept> extends JpaRepository<O,Long> {
}
