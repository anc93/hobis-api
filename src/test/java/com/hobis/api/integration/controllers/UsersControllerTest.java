package com.hobis.api.integration.controllers;

import com.google.gson.Gson;
import com.hobis.api.configuration.AbstractTestCase;
import com.hobis.api.entities.Users;
import com.hobis.api.repositories.UserRepository;
import com.hobis.api.services.UserService;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UsersControllerTest extends AbstractTestCase {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanupDb() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers() throws Exception {
        ArrayList<Users> createdUsers = new ArrayList<Users>();
        for (int i = 0; i < 3; i++) {
            Users user = new Users();
            user.setUsername("test user " + getTimeStampString());
            user.setTotalScore(Double.valueOf(getTimeStampString()));
            userRepository.save(user);
            createdUsers.add(user);
        }

        RequestBuilder request = MockMvcRequestBuilders.get("/api/users");
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();

        JSONArray fetchedUsers = new JSONArray(result.getResponse().getContentAsString());
        for (int i = 0; i < createdUsers.size(); i++) {
            Gson gson = new Gson();
            Users fetchedUser = gson.fromJson(fetchedUsers.getJSONObject(i).toString(), Users.class);
            Assertions.assertEquals(createdUsers.get(i).getId(), fetchedUser.getId());
            Assertions.assertEquals(createdUsers.get(i).getUsername(), fetchedUser.getUsername());
            Assertions.assertEquals(createdUsers.get(i).getTotalScore(), fetchedUser.getTotalScore());
        }
    }

    @Test
    void getUserById() throws Exception {
        Users user = new Users();
        user.setUsername("test user by id " + getTimeStampString());
        user.setTotalScore(Double.valueOf(getTimeStampString()));
        userRepository.save(user);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/users/" + user.getId());
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();

        Gson gson = new Gson();
        Users parsedUser = gson.fromJson(result.getResponse().getContentAsString(), Users.class);
        Assertions.assertEquals(user.getId(), parsedUser.getId());
        Assertions.assertEquals(user.getUsername(), parsedUser.getUsername());
        Assertions.assertEquals(user.getTotalScore(), parsedUser.getTotalScore());

    }

    @Test
    void addPerson() throws Exception {
        RequestBuilder createRequest = MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test add user\", \"totalScore\":\"1.2\"}");
        mockMvc.perform(createRequest).andReturn();
        ArrayList<Users> createdUser = (ArrayList<Users>) userRepository.findAll();
        RequestBuilder fetchByIdRequest = MockMvcRequestBuilders.get("/api/users/" + createdUser.get(0).getId());
        MvcResult result = mockMvc.perform(fetchByIdRequest).andExpect(status().isOk()).andReturn();

        Gson gson = new Gson();
        Users parsedUser = gson.fromJson(result.getResponse().getContentAsString(), Users.class);
        Assertions.assertEquals(createdUser.get(0).getId(), parsedUser.getId());
        Assertions.assertEquals("test add user", parsedUser.getUsername());
        Assertions.assertEquals(1.2, parsedUser.getTotalScore());
    }

    @Test
    void updateUserById() throws Exception {
        Users user = new Users();
        user.setUsername("test user " + getTimeStampString());
        user.setTotalScore(Double.valueOf(getTimeStampString()));
        userRepository.save(user);

        RequestBuilder updateRequest = MockMvcRequestBuilders.put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"updated test user\", \"totalScore\":\"1.2\"}");
        mockMvc.perform(updateRequest).andExpect(status().isOk()).andReturn();

        Optional<Users> fetchedUser = userRepository.findById(user.getId());
        Users updatedUser = fetchedUser.orElseGet(Users::new);
        Assertions.assertEquals("updated test user", updatedUser.getUsername());
        Assertions.assertEquals(1.2, updatedUser.getTotalScore());
    }

    @Test
    void deleteUserById() throws Exception {
        Users user = new Users();
        user.setUsername("test user " + getTimeStampString());
        user.setTotalScore(Double.valueOf(getTimeStampString()));
        userRepository.save(user);

        RequestBuilder updateRequest = MockMvcRequestBuilders.delete("/api/users/" + user.getId());
        mockMvc.perform(updateRequest).andExpect(status().isOk()).andReturn();

        Optional<Users> deletedUser = userRepository.findById(user.getId());
        Assertions.assertTrue(deletedUser.isEmpty());
    }
}