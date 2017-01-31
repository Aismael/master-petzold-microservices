package Order.Repositories;

import Order.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Boolean> findByMailIs(String mail);

    Account findByMail(String mail);

    Optional<Boolean> findByNameIs(String name);

    Account findByName(String name);

}
