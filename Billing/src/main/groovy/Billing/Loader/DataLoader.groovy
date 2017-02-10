package Billing.Loader

import Order.Entities.Account
import Order.Entities.Item
import Order.Entities.ItemSet
import Order.Entities.OrderConcepts.Favorite
import Order.Entities.OrderConcepts.Order
import Order.Repositories.AccountRepository
import Order.Repositories.ItemRepository
import Order.Repositories.OrderConcepts.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

/**
 * Created by Aismael on 04.12.2016.
 */
@Component
class DataLoader implements ApplicationRunner {


    @Autowired
    DataLoader() {
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
    }
}