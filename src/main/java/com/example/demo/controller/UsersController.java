package com.example.demo.controller;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Service.UserServiceImpl;
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

    @PostMapping("/users")
    public Users create(@RequestBody Users users) {
        return UserServiceImpl.getInstance().saveUser(users);
    }

}