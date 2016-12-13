package Order.Controller;

import Order.Entities.Item;
import Order.Entities.ItemSet;
import Order.Repositories.ItemRepository;
import Order.Repositories.ItemSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Aismael on 13.12.2016.
 */
@RestController
@RequestMapping("/ItemSet")
public class ItemSetController {

    @Autowired
    private ItemSetRepository itemSetRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<ItemSet> findItems() {
        return itemSetRepository.findAll();
    }
}
