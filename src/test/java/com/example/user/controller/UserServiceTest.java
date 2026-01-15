package com.example.user.controller;
import com.example.user.constant.Constants;
import com.example.user.constant.StatusCodes;
import com.example.user.entity.User;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;
import com.example.user.response.Status;
import com.example.user.response.UserResponse;
import com.example.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    // ---------- GET ALL USERS ----------

    @Test
    void getAllUsers_shouldReturnUsers() {
        when(repository.findAll()).thenReturn(
                List.of(
                        new User(1L, "John", "john@gmail.com"),
                        new User(2L, "Amit", "amit@gmail.com")
                )
        );

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(2);
        verify(repository).findAll();
    }

    // ---------- CREATE USER ----------

    @Test
    void createUser_shouldSaveUserAndReturnResponse() {
        User user = new User(null, "Amit", "amit@gmail.com");
        User savedUser = new User(1L, "Amit", "amit@gmail.com");

        when(repository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(user);

        assertThat(response.getUser().getId()).isEqualTo(1L);
        assertThat(response.getStatus().getStatus())
                .isEqualTo(StatusCodes.USER_CREATED_CODE);
        assertThat(response.getStatus().getMessage())
                .isEqualTo(Constants.USER_CREATED_SUCCESSFULLY);
    }

    // ---------- GET USER BY ID ----------

    @Test
    void getUserById_whenUserExists_shouldReturnResponse() {
        User user = new User(1L, "Amit", "amit@gmail.com");
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        assertThat(response.getUser()).isEqualTo(user);
        assertThat(response.getStatus().getStatus())
                .isEqualTo(StatusCodes.SUCCESS);
    }

    @Test
    void getUserById_whenUserNotFound_shouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(Constants.USER_NOT_FOUND);
    }

    // ---------- UPDATE USER ----------

    @Test
    void updateUser_whenUserExists_shouldUpdateAndReturnUser() {
        User existing = new User(1L, "Old", "old@gmail.com");
        User updated = new User(null, "New", "new@gmail.com");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(User.class))).thenReturn(existing);

        User result = userService.updateUser(1L, updated);

        assertThat(result.getName()).isEqualTo("New");
        assertThat(result.getEmail()).isEqualTo("new@gmail.com");
    }

    @Test
    void updateUser_whenUserNotFound_shouldReturnNull() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.updateUser(1L, new User());

        assertThat(result).isNull();
    }

    // ---------- DELETE USER ----------

    @Test
    void deleteUser_shouldDeleteAndReturnStatus() {
        Status status = userService.deleteUser(1L);

        assertThat(status.getStatus())
                .isEqualTo(StatusCodes.USER_DELETED_CODE);
        assertThat(status.getMessage())
                .isEqualTo(Constants.USER_DELETED_SUCCESSFULLY);

        verify(repository).deleteById(1L);
    }
}
