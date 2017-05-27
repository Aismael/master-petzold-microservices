package Billing.Repositories;

import Billing.Entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Martin Petzold on 18.02.2017.
 */
public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByName(String name);
}
