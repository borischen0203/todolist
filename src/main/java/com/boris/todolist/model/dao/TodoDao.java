package com.boris.todolist.model.dao;

import com.boris.todolist.model.entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoDao extends CrudRepository<Todo, Integer> {

}