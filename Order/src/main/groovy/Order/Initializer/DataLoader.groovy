package Order.Initializer

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
    private ItemRepository itemRepository
    private OrderRepository orderRepository
    private AccountRepository accountRepository

    @Autowired
    DataLoader(ItemRepository itemRepository,OrderRepository orderRepository,AccountRepository accountRepository) {
        this.itemRepository = itemRepository
        this.orderRepository=orderRepository
        this.accountRepository=accountRepository
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
        Item i1=new Item(name: "Pizza Margherita",details: "Die standard Pizza",allergens: false)
        Item i2=new Item(name: "Hamburger",details: "Ein Burger ohne Käse",allergens: true)
        Order o=new Order(date:new Date())
        Favorite f=new Favorite(count: 5)
        ItemSet is1=new ItemSet(orderConcept: o,item: i1)
        ItemSet is2=new ItemSet(orderConcept: o,item: i2,count: 2)
        ItemSet is3=new ItemSet(orderConcept: o,item: i1,count: 2)
        ItemSet is4=new ItemSet(orderConcept: o,item: i2)
        Account a=new  Account(name: "First",mail: "aismaelinc@gmail.com")
        a.getOrders().add(o)
        a.getFavorites().add(f)
        accountRepository.save(a)
    }
}