package com.boris.todolist.controller;

import com.boris.todolist.model.entity.Todo;
import com.boris.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping("/todos")
    public Iterable<Todo> getTodoList() {
        Iterable<Todo> todoList = todoService.getTodo();
        return todoList;
    }

}
