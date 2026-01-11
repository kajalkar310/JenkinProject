package com.example.user.controller;

import com.example.user.response.Status;
import com.example.user.response.UserResponse;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.user.repository.UserRepository;
import com.example.user.entity.User;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    public UserService service;


    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }


    @PostMapping("/create")
    public UserResponse createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/getUser/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return service.updateUser(id, userDetails);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Status> deleteUser(@PathVariable Long id) {
        return  ResponseEntity.ok(service.deleteUser(id));
    }



}
