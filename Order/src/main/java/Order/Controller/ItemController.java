package Order.Controller;

import Order.Entities.Item;
import Order.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Aismael on 13.12.2016.
 */
@RestController
@RequestMapping("/Item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @RequestMapping(method = RequestMethod.GET)
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
