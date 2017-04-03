package cookbook.Repositories;

import cookbook.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aismael on 03.04.2017.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}

