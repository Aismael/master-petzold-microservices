package Order.Controller;

import Order.DTOs.ItemSetStubDto;
import Order.DTOs.OrderDto;
import Order.Entities.Account;
import Order.Entities.ItemSet;
import Order.Entities.OrderConcepts.Favorite;
import Order.Entities.OrderConcepts.Order;
import Order.Repositories.AccountRepository;
import Order.Repositories.ItemRepository;
import Order.Repositories.OrderConcepts.FavoriteRepository;
import Order.Repositories.OrderConcepts.OrderRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Created by Aismael on 30.01.2017.
 */
@RestController
@RequestMapping(path = "${RESTConfiguration.view.order.path}")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AccountRepository accountRepository;


    @PostConstruct
    public void initializeBean() {
        System.out.println("_________________");
    }


    @RequestMapping(value = "${RESTConfiguration.view.order.all.path}")
    public List<Order> findFavorites() {
        return orderRepository.findAll();
    }

    @RequestMapping(value = "${RESTConfiguration.view.order.all.path}" +
            "${RESTConfiguration.view.order.all.account.path}" + "/{accountId}")
    public List<Order> findFavoritesByAccountId(@PathVariable Long accountId) {
        return orderRepository.findAllByAccountId(accountId);
    }


    @RequestMapping(value = "${RESTConfiguration.view.order.one.path}", method = RequestMethod.POST)
    public Long orderFavorite(@RequestBody OrderDto orderDto) {

        Order order = new Order(accountRepository.findOne(orderDto.getAccountId()), new Date());
        for (ItemSetStubDto itemSetStub : orderDto.getitemSetStubDtos()) {
            ItemSet i=new ItemSet();
            i.setItem(itemRepository.getOne(itemSetStub.getItemID()));
            i.setCount(itemSetStub.getCount());
            order.getItemSets().add(i);
        }

        orderRepository.saveAndFlush(order);
        return orderRepository.getOne(order.getId()).getId();
    }


}

