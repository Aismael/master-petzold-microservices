package Billing.Repositories;

import Billing.Entities.Account;
import Billing.Entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aismael on 18.02.2017.
 */
public interface PositionRepository extends JpaRepository<Position, Long> {
}
