package Billing.Repositories;

import Billing.Entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Martin Petzold on 18.02.2017.
 */
public interface PositionRepository extends JpaRepository<Position, Long> {
}
