package in.bushansingur.springbootmongodb.controller;


import in.bushansingur.springbootmongodb.exception.TodoCollectionException;
import in.bushansingur.springbootmongodb.model.TodoDTO;
import in.bushansingur.springbootmongodb.repository.TodoRepository;
import in.bushansingur.springbootmongodb.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController

public class TodoController {
    @Autowired
        private TodoRepository todoRepo;
    @Autowired
        private TodoService todoService;

    @GetMapping("/todos")
         public ResponseEntity<?> getAllTodos() {
             List<TodoDTO> todos  = todoService.getAllTodos();
        return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
         }
@PostMapping("/todos")
public ResponseEntity<?> create (@RequestBody TodoDTO todo){
        try {

            todoService.createTodo(todo);
            return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
        }catch (ConstraintViolationException e ) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
}
@GetMapping("/todos/{id}")
public ResponseEntity<?>getSingleTodo(@PathVariable("id")String id){
    try {
        return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
    } catch (TodoCollectionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    }
    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id")String id,@RequestBody TodoDTO todo){

        try {
            todoService.updateTodo(id,todo);
            return new ResponseEntity<>("Updated movie with id "+id+"", HttpStatus.OK);
        }
        catch(ConstraintViolationException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try{
            todoService.deleteTodoById(id);
            return new ResponseEntity<>("Successfully deleted todo with id "+id, HttpStatus.OK);
        }
        catch (TodoCollectionException  e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}

