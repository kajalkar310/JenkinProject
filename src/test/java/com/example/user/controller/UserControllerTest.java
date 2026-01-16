package com.example.user.controller;

import com.example.user.component.JwtFilter;
import com.example.user.component.JwtUtil;
import com.example.user.entity.User;
import com.example.user.response.Status;
import com.example.user.response.UserResponse;
import com.example.user.service.CustomUserDetailsService;
import com.example.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    // ---------- GET ALL USERS ----------

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        when(service.getAllUsers())
                .thenReturn(List.of(new User(1L, "Amit", "amit@gmail.com")));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Amit"));
    }

    // ---------- CREATE USER ----------

    @Test
    void createUser_shouldReturnUserResponse() throws Exception {
        User user = new User(null, "Amit", "amit@gmail.com");
        UserResponse response =
                new UserResponse(new Status("USR_201", "User created successfully"),
                        new User(1L, "Amit", "amit@gmail.com"));

        when(service.createUser(any(User.class))).thenReturn(response);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.status").value("USR_201"));
    }

    // ---------- GET USER BY ID ----------

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        UserResponse response =
                new UserResponse(new Status("SUCCESS", "User found"),
                        new User(1L, "Amit", "amit@gmail.com"));

        when(service.getUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/users/getUser/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value("Amit"));
    }

    // ---------- UPDATE USER ----------

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        User updated = new User(1L, "New", "new@gmail.com");

        when(service.updateUser(any(Long.class), any(User.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"));
    }

    // ---------- DELETE USER ----------

    @Test
    void deleteUser_shouldReturnStatus() throws Exception {
        when(service.deleteUser(1L))
                .thenReturn(new Status("USR_200", "User deleted successfully"));

        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("USR_200"));
    }
}
