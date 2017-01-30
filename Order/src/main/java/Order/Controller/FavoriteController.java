package Order.Controller;

import Order.Entities.ItemSet;
import Order.Entities.OrderConcepts.Favorite;
import Order.Entities.OrderConcepts.Order;
import Order.Repositories.OrderConcepts.FavoriteRepository;
import Order.Repositories.OrderConcepts.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Created by Aismael on 30.01.2017.
 */
@RestController
@RequestMapping(path = "${RESTConfiguration.view.favorite.path}")
public class FavoriteController {
        @Autowired
        private FavoriteRepository favoriteRepository;
        @Autowired
        private OrderRepository orderRepository;

        @PostConstruct
        public void initializeBean() {
            System.out.println("_________________");
        }


        @RequestMapping(value = "${RESTConfiguration.view.favorite.all.path}")
        public List<Favorite> findFavorites() {
            return favoriteRepository.findAll();
        }

        @RequestMapping(value = "${RESTConfiguration.view.favorite.all.path}"+
                "${RESTConfiguration.view.favorite.all.account.path}"+"/{accountId}")
        public List<Favorite> findFavoritesByAccountId(@PathVariable Long accountId) {
            return favoriteRepository.findAllByAccountId(accountId);
        }

    @RequestMapping(value = "${RESTConfiguration.view.favorite.one.path}"+
            "${RESTConfiguration.view.favorite.one.order.path}"+"/{favoriteId}", method = RequestMethod.POST)
    public Order orderFavorite(@PathVariable Long favoriteId){

         Favorite favorite= favoriteRepository.getOne(favoriteId);
         favorite.setCount(favorite.getCount()+1);
         Order o=new Order();
         o.setAccount(favorite.getAccount().getOne());
         o.setPosted(false);
         o.setDate(new Date());
         for (ItemSet itemset:favorite.getItemSets().getAll()) {
         ItemSet i= new ItemSet();
         i.setItem(itemset.getItem().getOne());
         i.setCount(itemset.getCount());
         o.getItemSets().add(i);
         }
         orderRepository.saveAndFlush(o);
         favoriteRepository.saveAndFlush(favorite);
         return o;
    }
    }

