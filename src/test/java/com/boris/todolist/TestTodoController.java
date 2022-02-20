package com.boris.todolist;


import com.boris.todolist.model.entity.Todo;
import com.boris.todolist.service.TodoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        List<Todo> expectedList = new ArrayList();
        Todo todo = new Todo();
        todo.setTask("Washing");
        todo.setId(1);
        expectedList.add(todo);

        // Mock todoService
        Mockito.when(todoService.getTodos()).thenReturn(expectedList);

        // [act] Mock "GET" /api/todos
        String returnString = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/todos")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Iterable<Todo> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Todo>>() {
        });

        // [Assert]
        assertEquals(expectedList, actualList);
    }

}
