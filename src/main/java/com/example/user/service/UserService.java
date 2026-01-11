package com.example.user.service;


import com.example.user.constant.Constants;
import com.example.user.constant.StatusCodes;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.response.Status;
import com.example.user.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.user.exception.UserNotFoundException;

import java.net.StandardSocketOptions;
import java.util.List;

@Service
public class UserService {


    @Autowired
    UserRepository repository;

    public List<User> getAllUsers() {

        return repository.findAll();
    }
    public UserResponse createUser(User user) {

        UserResponse response = new UserResponse();


        User createdUser= repository.save(user);
        Status status = new Status(StatusCodes.USER_CREATED_CODE,Constants.USER_CREATED_SUCCESSFULLY);
        response.setStatus(status);
        response.setUser(createdUser);
        return response;

    }

    public UserResponse getUserById(Long id) {

        UserResponse response = new UserResponse();
        Status status;
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND));
        status = new Status(StatusCodes.SUCCESS, Constants.USER_FOUND);
        response.setStatus(status);
        response.setUser(user);
        return response;
    }




    public Status deleteUser(Long id) {
        repository.deleteById(id);
        return  new Status(StatusCodes.USER_DELETED_CODE, Constants.USER_DELETED_SUCCESSFULLY);

    }

    public User updateUser(Long id, User userDetails) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return repository.save(user);
        }
        return user;
    }

}
