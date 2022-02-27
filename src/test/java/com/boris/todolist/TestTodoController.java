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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
//        // [arrange]
//        Todo mockTodo = new Todo();
//        mockTodo.setId(1);
//        mockTodo.setTask("Washing");
//        mockTodo.setStatus(1);
//
//        JSONObject request = new JSONObject();
//        request.put("id", 1);
//        request.put("task", "Washing");
//
//        Mockito.when(todoService.createTodo(mockTodo)).thenReturn(1);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//        RequestBuilder requestBuilder =
//                MockMvcRequestBuilders
//                        .post("/api/todos")
//                        .headers(httpHeaders)
//                        .content(request.toString());
//
//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isCreated())
////                .andExpect(jsonPath("$.id").hasJsonPath())
//                .andExpect(jsonPath("$.id").value(request.getInt("id")));
////                .andExpect(jsonPath("$.price").value(request.getInt("price")))
////                .andExpect(header().exists(HttpHeaders.LOCATION))
////                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.id").value(request.getInt("id")));
//                .andReturn().getResponse().getContentAsString();

    }
}
