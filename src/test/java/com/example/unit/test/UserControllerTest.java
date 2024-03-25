package com.example.unit.test;

import com.example.unit.test.controllers.UserController;
import com.example.unit.test.entities.User;
import com.example.unit.test.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
    }

    //Test per la creazione di un nuovo User
    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setNome("Michele");
        user.setCognome("Mangiacotti");
        user.setEmail("michlele.mangicotti_Java@develhope.com");
        user.setAge(38);

        String userJSON = objectMapper.writeValueAsString(user);
        MvcResult result = this.mockMvc.perform(post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();


    }

    //Test per avere la lista completa degli User
    @Test
    void getAllStudent() throws Exception {
        createUser();
        MvcResult result = this.mockMvc.perform(get("/user/getAllUser"))
                .andDo(print()).andReturn();

        List<User> userFromResponseList = objectMapper.readValue(result.getResponse().getContentAsString(),
                List.class);
        assertThat(userFromResponseList.size()).isNotZero();
    }

    //Test per cercare uno User tramite id
    @Test
    void getUserId() throws Exception {
        Long userId = 1L;
        createUser();

        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserId/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId)).andReturn();
    }

    //Test per aggiornare uno user tramite id
    @Test
    void updateUserId() throws Exception {
        Long userId = 1L;
        createUser();
        User updateUser = new User(userId, "Denise", "De Leo", "denise.dl@gmail.com", 28);
        String studentJSON = objectMapper.writeValueAsString(updateUser);

        MvcResult resultUpdate = this.mockMvc.perform(MockMvcRequestBuilders.put("/user/updateUserId/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON).content(studentJSON))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String content = resultUpdate.getResponse().getContentAsString();
        Assertions.assertNotNull(content);

    }
    //Test per eliminare uno studente tramite id
    @Test
    void deleteStudente() throws Exception {
        createUser();
        Long userId = 1L;

        MvcResult result = mockMvc.perform(delete("/user/deleteUserId/{id}",userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


    }

}
