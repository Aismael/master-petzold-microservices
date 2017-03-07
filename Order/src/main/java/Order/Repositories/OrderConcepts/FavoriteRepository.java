package Order.Repositories.OrderConcepts;

import Order.Entities.OrderConcepts.Favorite;
import Order.Repositories.OrderConceptRepository;

import java.util.List;

/**
 * Created by Martin Petzold on 04.12.2016.
 */
public interface FavoriteRepository extends OrderConceptRepository<Favorite> {
    List<Favorite> findAllByAccountId(Long accountId);

}
