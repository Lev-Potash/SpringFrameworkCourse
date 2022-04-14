package com.example.demo.controller;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Service.UserServiceImpl;
import com.example.demo.collection.UserProtected;
import com.example.demo.model.Task;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsersController {
    public static final String SUCCESS_DELETE_MESSAGE = "Система выполнила успешне удаление задачи: ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/users")
    public Users create(@RequestBody Users users) {
        return userService.create(users);
    }

    @GetMapping(value = "/users/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserProtected> getCurrentUser() {
        return userService.getProtectedUser();
    }


    @GetMapping("/main-user")
    public String getMainUser() {
        return "main-user";

    }

    @GetMapping("/main-admin")
    public String getMainAdmin() {
        return "main-admin";

    }

}