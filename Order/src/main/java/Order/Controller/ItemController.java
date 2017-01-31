package Order.Controller;

import Exeptions.AccountWithThisMailNotFoundException;
import Exeptions.AccountWithThisNameNotFoundException;
import Exeptions.MailAllreadyInUseException;
import Exeptions.NameAllreadyInUseException;
import Order.Entities.Account;
import Order.Entities.Item;
import Order.Repositories.AccountRepository;
import Order.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Aismael on 13.12.2016.
 */

@RestController
@RequestMapping(path = "${RESTConfiguration.view.item.path}")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;


    @PostConstruct
    public void initializeBean() {
        System.out.println("_________________");
        System.out.println("_________________");

    }


    @RequestMapping(value = "${RESTConfiguration.view.item.all.path}")
    public List<Item> findItems() {
        return itemRepository.findAll();
    }


}

