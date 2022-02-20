package com.boris.todolist;

import com.boris.todolist.model.dao.TodoDao;
import com.boris.todolist.model.entity.Todo;
import com.boris.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class TestTodoService {
    @Autowired
    TodoService todoService;

    @MockBean
    TodoDao todoDao;


    @Test
    public void testGetTodos() {
        //3A: arrange, act, assert
        // [Arrange]
        List<Todo> expectedTodosList = new ArrayList();
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Washing");
        todo.setStatus(1);
        expectedTodosList.add(todo);

        // mock todoDao.finAll function result
        Mockito.when(todoDao.findAll()).thenReturn(expectedTodosList);

        // [Act]Operate todoService.getTodos();
        Iterable<Todo> actual = todoService.getTodos();

        // [Assert]
        assertEquals(expectedTodosList, actual);

    }

    @Test
    public void testCreateTodo() {
        // [Arrange]
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(1);

        Mockito.when(todoDao.save(todo)).thenReturn(todo);

        // [Act]
        Integer actual = todoService.createTodo(todo);
        Integer expected = todo.getId();
        //  [Assert]
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateTodoSuccess() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(1);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(1)).thenReturn(resultTodo);

        // [Arrange]
        todo.setStatus(2);

        // [Act]
        Boolean actualUpdateRlt = todoService.updateTodo(1, todo);

        //  [Assert]
        assertEquals(true, actualUpdateRlt);
    }

    @Test
    public void testUpdateTodoNotExistId() {
        // Request body
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(2);
        Optional<Todo> resultTodo = Optional.of(todo);

        // Mock id not found
        Mockito.when(todoDao.findById(1)).thenReturn(Optional.empty());

        // [Act]
        Boolean actualUpdateRlt = todoService.updateTodo(1, todo);

        // [Assert]
        assertEquals(false, actualUpdateRlt);
    }

    @Test
    public void testUpdateTodoOccurException() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setStatus(1);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(1)).thenReturn(resultTodo);
        todo.setStatus(2);

        // Mock NullPointerException
        doThrow(NullPointerException.class).when(todoDao).save(todo);

        // [Act]
        Boolean actualUpdateRlt = todoService.updateTodo(100, todo);

        //  [Assert]
        assertEquals(false, actualUpdateRlt);
    }

    @Test
    public void testDeleteTodoSuccess() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(2);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(1)).thenReturn(resultTodo);

        // [Act]
        Boolean actualDeleteRlt = todoService.deleteTodo(1);

        //  [Assert]
        assertEquals(true, actualDeleteRlt);
    }

    @Test
    public void testDeleteTodoIdNotExist() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(2);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(100)).thenReturn(Optional.empty());

        // [Act]
        Boolean actualDeleteRlt = todoService.deleteTodo(1);

        //  [Assert]
        assertEquals(false, actualDeleteRlt);
    }

    @Test
    public void testDeleteTodoOccurException() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setStatus(1);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(1)).thenReturn(resultTodo);
        todo.setStatus(2);

        // Mock NullPointerException
        doThrow(NullPointerException.class).when(todoDao).save(todo);

        // [Act]
        Boolean actualUpdateRlt = todoService.updateTodo(100, todo);

        //  [Assert]
        assertEquals(false, actualUpdateRlt);
    }

}
