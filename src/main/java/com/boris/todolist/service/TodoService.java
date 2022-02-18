package com.boris.todolist.service;


import com.boris.todolist.model.dao.TodoDao;
import com.boris.todolist.model.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    TodoDao todoDao;

    //Get all to do items
    public Iterable<Todo> getTodos() {
        return todoDao.findAll();
    }

    public Integer createTodo(Todo todo) {
        Todo resultTodo = todoDao.save(todo);
        return resultTodo.getId();
    }

    public Boolean updateTodo(Integer id, Todo todo) {
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
        todoDao.save(newTodo);// save update to_do to DB
        return true;
    }

    //check the id exist or not
    public Optional<Todo> findById(Integer id) {
        Optional<Todo> todo = todoDao.findById(id);
        return todo;
    }


    public Boolean deleteTodo(Integer id) {
        Optional<Todo> isExistTodo = findById(id);
        if (!isExistTodo.isPresent()) {
            return false;
        }
        todoDao.deleteById(id);
        return true;
    }
}
