package com.boris.todolist;

import com.boris.todolist.model.entity.Todo;
import com.boris.todolist.service.TodoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTodoController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean //mock service
    TodoService todoService;

    @Test
    public void testGetTodos() throws Exception {
        // [Arrange]
        List<Todo> expectedList = new ArrayList<>();
        Todo todo = new Todo();
        todo.setTask("Washing");
        todo.setId(1);
        expectedList.add(todo);

        Mockito.when(todoService.getTodos()).thenReturn(expectedList);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/api/todos")
                        .headers(httpHeaders);


        String returnString = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Iterable<Todo> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Todo>>() {
        });

        // [Assert]
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testCreateTodo() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // [arrange]

        Todo mockTodo = new Todo();
        mockTodo.setId(1);
        mockTodo.setTask("Washing");
        mockTodo.setStatus(1);

        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("task", "Washing");

        Mockito.when(todoService.createTodoService(Mockito.any(Todo.class))).thenReturn(Optional.of(mockTodo));


        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post("/api/todos")
                        .headers(httpHeaders)
                        .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(request.getString("id")))
                .andExpect(jsonPath("$.task").value(request.getString("task")));

    }

    @Test
    public void testUpdateTodoSuccess() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Todo mockTodo = new Todo();
        mockTodo.setId(1);
        mockTodo.setTask("Washing");
        mockTodo.setStatus(1);


        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("task", "Washing");
        request.put("status", 1);

        Mockito.when(todoService.updateTodoService(Mockito.anyInt(), Mockito.any(Todo.class))).thenReturn(true);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .put("/api/todos/" + mockTodo.getId())
                        .headers(httpHeaders)
                        .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateTodoFail() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Todo mockTodo = new Todo();
        mockTodo.setId(1);
        mockTodo.setTask("Washing");
        mockTodo.setStatus(1);


        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("task", "Washing");
        request.put("status", 1);

        Mockito.when(todoService.updateTodoService(Mockito.anyInt(), Mockito.any(Todo.class))).thenReturn(false);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .put("/api/todos/" + mockTodo.getId())
                        .headers(httpHeaders)
                        .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testDeleteTodoSuccess() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Todo mockTodo = new Todo();
        mockTodo.setId(1);
        mockTodo.setTask("Washing");
        mockTodo.setStatus(1);


        Mockito.when(todoService.deleteTodoService(Mockito.anyInt())).thenReturn(true);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .delete("/api/todos/" + mockTodo.getId())
                        .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTodoFail() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Todo mockTodo = new Todo();
        mockTodo.setId(1);
        mockTodo.setTask("Washing");
        mockTodo.setStatus(1);


        Mockito.when(todoService.deleteTodoService(Mockito.anyInt())).thenReturn(false);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .delete("/api/todos/" + mockTodo.getId())
                        .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
