package Billing.Repositories;
import Billing.Entities.XOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aismael on 18.02.2017.
 */
public interface XOrderRepository extends JpaRepository<XOrder, Long> {
    XOrder findByIdAndAccountId(Long id,Long accountId);
}
