package Order.Repositories;

import Order.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface AccountRepository extends JpaRepository<Account,Long> {
}
