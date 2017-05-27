package Billing.Repositories;

import Billing.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Martin Petzold on 18.02.2017.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
