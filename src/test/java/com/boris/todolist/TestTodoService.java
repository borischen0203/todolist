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
        List<Todo> expectedTodosList = new ArrayList<>();
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Washing");
        todo.setStatus(1);
        expectedTodosList.add(todo);

        Mockito.when(todoDao.findAll()).thenReturn(expectedTodosList);

        Iterable<Todo> actual = todoService.getTodos();

        assertEquals(expectedTodosList, actual);
    }

    @Test
    public void testCreateTodo() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(1);
        Optional<Todo> expected = Optional.of(todo);

        Mockito.when(todoDao.save(todo)).thenReturn(todo);

        Optional<Todo> actual = todoService.createTodoService(todo);

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

        Boolean actual = todoService.updateTodoService(1, todo);

        assertEquals(true, actual);
    }

    @Test
    public void testUpdateTodoNotExistId() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(2);

        Mockito.when(todoDao.findById(1)).thenReturn(Optional.empty());

        Boolean actual = todoService.updateTodoService(1, todo);

        assertEquals(false, actual);
    }

    @Test
    public void testUpdateTodoException() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setStatus(1);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(1)).thenReturn(resultTodo);
        doThrow(NullPointerException.class).when(todoDao).save(todo);

        Boolean actual = todoService.updateTodoService(100, todo);

        assertEquals(false, actual);
    }

    @Test
    public void testDeleteTodoSuccess() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(2);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(1)).thenReturn(resultTodo);

        Boolean actual = todoService.deleteTodoService(1);

        assertEquals(true, actual);
    }

    @Test
    public void testDeleteTodoIdNotExist() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Write");
        todo.setStatus(2);

        Mockito.when(todoDao.findById(100)).thenReturn(Optional.empty());

        Boolean actual = todoService.deleteTodoService(1);

        assertEquals(false, actual);
    }

    @Test
    public void testDeleteTodoException() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setStatus(1);
        Optional<Todo> resultTodo = Optional.of(todo);

        Mockito.when(todoDao.findById(1)).thenReturn(resultTodo);

        doThrow(NullPointerException.class).when(todoDao).save(todo);

        Boolean actual = todoService.updateTodoService(100, todo);

        assertEquals(false, actual);
    }

}
