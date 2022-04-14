package com.example.demo.Service;

import com.example.demo.collection.UserProtected;
import com.example.demo.model.Users;

import java.util.List;

public interface UserService {
    Users saveUser(Users users);
    Users getCurrentUser();
    Users create(Users user);
    List<UserProtected> getProtectedUser();
}
