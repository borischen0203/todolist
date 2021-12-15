package com.boris.todolist.service;


import com.boris.todolist.model.dao.TodoDao;
import com.boris.todolist.model.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class TodoService {
    @Autowired
    TodoDao todoDao;

    public Iterable<Todo> getTodo() {
        return todoDao.findAll();
    }

    public Iterable<Todo> createTodo(Todo todo) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = df.format(new Date());
        todo.setCreateTime(date);
        todo.setUpdateTime(date);
        todoDao.save(todo);
        return getTodo();
    }
}
