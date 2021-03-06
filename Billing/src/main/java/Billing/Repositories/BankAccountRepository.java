package Billing.Repositories;

import Billing.Entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Martin Petzold on 18.02.2017.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findAllByAccountId(Long accountId);
}
