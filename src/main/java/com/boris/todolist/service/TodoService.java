package com.boris.todolist.service;


import com.boris.todolist.controller.TodoController;
import com.boris.todolist.model.dao.TodoDao;
import com.boris.todolist.model.entity.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    TodoDao todoDao;

    //Get all to do items
    public Iterable<Todo> getTodos() {
        return todoDao.findAll();
    }

    public Optional<Todo> createTodoService(Todo todo) {
        Todo resultTodo = todoDao.save(todo);
        logger.info("[createTodo service] request:", resultTodo);
        return Optional.of(resultTodo);
    }

    public Boolean updateTodoService(Integer id, Todo todo) {
        Optional<Todo> isExistTodo = findById(id);

        //check exist
        if (!isExistTodo.isPresent()) {
            return false;
        }

        //check to_do request is not null
        if (todo.getStatus() == null) {
            return false;
        }
        Todo newTodo = isExistTodo.get();
        newTodo.setStatus(todo.getStatus());// updatae status from To_Do request
        try {
            todoDao.save(newTodo);// save update to_do to DB
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //check the id exist or not
    public Optional<Todo> findById(Integer id) {
        Optional<Todo> todo = todoDao.findById(id);
        return todo;
    }

    public Boolean deleteTodoService(Integer id) {
        Optional<Todo> isExistTodo = findById(id);
        if (!isExistTodo.isPresent()) {
            return false;
        }
        try {
            todoDao.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
