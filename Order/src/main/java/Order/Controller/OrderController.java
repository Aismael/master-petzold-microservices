package Order.Controller;

import Order.DTOs.ItemSetStubDTO;
import Order.DTOs.OrderDTO;
import Order.DTOs.OrderDTOList;
import Order.Entities.ItemSet;
import Order.Entities.OrderConcepts.Order;
import Order.Repositories.AccountRepository;
import Order.Repositories.ItemRepository;
import Order.Repositories.OrderConcepts.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Controller der Bestellungen
 * Created by Martin Petzold on 30.01.2017.
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


    /**
     * Liste aller Bestellungen
     * @return Liste der Bestellungen
     */
    @RequestMapping(value = "${RESTConfiguration.view.order.all.path}")
    public OrderDTOList findFavorites() {
        return new OrderDTOList (orderRepository.findAll());
    }

    /**
     * Alle Bestellungen eines Accounts
     * @param accountId
     * @return Liste der Bestellungen
     */
    @RequestMapping(value = "${RESTConfiguration.view.order.all.path}" +
            "${RESTConfiguration.view.order.all.account.path}" + "/{accountId}")
    public OrderDTOList findFavoritesByAccountId(@PathVariable Long accountId) {
        return new OrderDTOList (orderRepository.findAllByAccountId(accountId));
    }


    /**
     *  Speichert eine Bestellung aus einem DTO
     * @param orderDTO
     * @return gespeicherte Bestellung
     */
    @RequestMapping(value = "${RESTConfiguration.view.order.one.path}", method = RequestMethod.POST)
    public Long orderFavorite(@RequestBody OrderDTO orderDTO) {
        Order order = new Order(accountRepository.findOne(orderDTO.getAccountId()), new Date());
        for (ItemSetStubDTO itemSetStub : orderDTO.getItemSetStubDTOS()) {
            ItemSet i=new ItemSet();
            i.setItem(itemRepository.getOne(itemSetStub.getItemID()));
            i.setCount(itemSetStub.getCount());
            order.getItemSets().add(i);
        }
        orderRepository.saveAndFlush(order);
        return orderRepository.getOne(order.getId()).getId();
    }


}

