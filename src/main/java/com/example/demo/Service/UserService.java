package com.example.demo.Service;

import com.example.demo.model.Users;

public interface UserService {
    Users saveUser(Users users);
    Users getCurrentUser();
    Users create(Users user);
}
