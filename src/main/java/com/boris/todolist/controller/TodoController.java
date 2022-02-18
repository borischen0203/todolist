package com.boris.todolist.controller;

import com.boris.todolist.model.entity.Todo;
import com.boris.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api") //father path ex: /api/todos
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity getTodos() {
        Iterable<Todo> todoList = todoService.getTodos();
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

    @GetMapping("/todos/{id}")
    public Optional<Todo> getTodo(@PathVariable Integer id) {
        Optional<Todo> todo = todoService.findById(id);
        return todo;
    }

    @PostMapping("/todos")
    public ResponseEntity createTodo(@RequestBody Todo todo) {
        Integer result = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @PutMapping("/todos/{id}")
    public ResponseEntity upadteTodo(@PathVariable Integer id, @RequestBody Todo todo) {
        Boolean result = todoService.updateTodo(id, todo);
        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status can not be empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity deleteTodo(@PathVariable Integer id) {
        Boolean result = todoService.deleteTodo(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id not exist");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

}
