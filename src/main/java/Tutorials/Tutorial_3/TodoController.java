package Tutorials.Tutorial_3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> findItems() {
        return repo.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Todo addItem(@RequestBody Todo todo) {
        todo.setId(null);
        return repo.saveAndFlush(todo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Todo updateItem(@RequestBody Todo updatedTodo, @PathVariable Long id) {
        updatedTodo.setId(id);
        return repo.saveAndFlush(updatedTodo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable Long id) {
        repo.delete(id);
    }
}
