package Order.Initializer

import Order.Entities.Item
import Order.Entities.Todo
import Order.Repositories.ItemRepository
import Order.Repositories.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

/**
 * Created by Aismael on 04.12.2016.
 */
@Component
class DataLoader implements ApplicationRunner {
    private TodoRepository todoRepository
    private ItemRepository itemRepository

    @Autowired
    DataLoader(TodoRepository todoRepository, ItemRepository itemRepository) {
        this.todoRepository = todoRepository
        this.itemRepository = itemRepository
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
        todoRepository.save(new Todo(checked: false, description: "Roll an immense boulder up a hill."))
        todoRepository.save(new Todo(checked: true, description: "Roll an immense boulder down a hill."))
        itemRepository.save(new Item(name: "Test"))
        itemRepository.save(new Item(name: "Hamburger"))

    }
}