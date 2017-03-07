package Order.Controller;

import Order.DTOs.FavoriteDto;
import Order.DTOs.ItemSetStubDto;
import Order.DTOs.OrderDto;
import Order.Entities.ItemSet;
import Order.Entities.OrderConcepts.Favorite;
import Order.Entities.OrderConcepts.Order;
import Order.Repositories.AccountRepository;
import Order.Repositories.ItemRepository;
import Order.Repositories.ItemSetRepository;
import Order.Repositories.OrderConcepts.FavoriteRepository;
import Order.Repositories.OrderConcepts.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Kontroller für die Favoriten
 * Created by Martin Petzold on 30.01.2017.
 */
@RestController
@RequestMapping(path = "${RESTConfiguration.view.favorite.path}")
public class FavoriteController {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ItemSetRepository itemSetRepository;

    @PostConstruct
    public void initializeBean() {
        System.out.println("_________________");
    }

    /**
     * gibt eine Liste aller Favoriten zurück
     * @return Liste der Favoriten
     */
    @RequestMapping(value = "${RESTConfiguration.view.favorite.all.path}")
    public List<Favorite> findFavorites() {
        return favoriteRepository.findAll();
    }

    /**
     * Gibt die Liste aller Favoriten eines Accounts zurück
     * @param accountId Id des Accounts
     * @return Liste der zugehörigen Favoriten
     */
    @RequestMapping(value = "${RESTConfiguration.view.favorite.all.path}" +
            "${RESTConfiguration.view.favorite.all.account.path}" + "/{accountId}")
    public List<Favorite> findFavoritesByAccountId(@PathVariable Long accountId) {
        return favoriteRepository.findAllByAccountId(accountId);
    }

    /**
     * Erstellt aus einem Favoriten eine Bestellung
     * @param favoriteId Id des Favoriten
     * @return die erstellte Bestellung
     */
    @RequestMapping(value = "${RESTConfiguration.view.favorite.one.path}" +
            "${RESTConfiguration.view.favorite.one.order.path}" + "/{favoriteId}", method = RequestMethod.POST)
    public Order orderFavorite(@PathVariable Long favoriteId) {
        Favorite favorite = favoriteRepository.getOne(favoriteId);
        favorite.setCount(favorite.getCount() + 1);
        Order order = new Order(favorite.getAccount().getOne(), new Date());
        for (ItemSet itemset : favorite.getItemSets().getAll()) {
            ItemSet i = new ItemSet();
            i.setItem(itemset.getItem().getOne());
            i.setCount(itemset.getCount());
            order.getItemSets().add(i);
        }
        orderRepository.saveAndFlush(order);
        favoriteRepository.saveAndFlush(favorite);
        return order;
    }

    /**
     * Speichert einen Favoriten
     * @param favoriteDto das DTO asu dem ein Favorit gemacht wird
     * @return der Gespeicherte Favorit
     */
    @RequestMapping(value = "${RESTConfiguration.view.favorite.one.path}", method = RequestMethod.POST)
    public Long orderFavorite(@RequestBody FavoriteDto favoriteDto) {
        Favorite favorite = new Favorite(accountRepository.findOne(favoriteDto.getAccountId()),favoriteDto.getName(),favoriteDto.getCount());
        for (ItemSetStubDto itemSetStub : favoriteDto.getItemSetStubDtos()) {
            ItemSet i=new ItemSet();
            i.setItem(itemRepository.getOne(itemSetStub.getItemID()));
            i.setCount(itemSetStub.getCount());
            favorite.getItemSets().add(i);

        }
        favoriteRepository.saveAndFlush(favorite);
        return favoriteRepository.getOne(favorite.getId()).getId();
    }
}

