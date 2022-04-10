package com.example.demo.Service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * На этот раз, нужно немного переделать создание пользователя. Добавим шифрование пароля.
 * И также добавим метод, который возвращает текущего пользователя.
 *
 * Логин пользователя можно получить из объекта SecurityContextHolder.getContext().getAuthentication().
 * Далее, достаем пользователя по логину с БД
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users saveUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Users create(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return userRepository.findByLogin(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }
}



/*public class UserServiceImpl implements UserService {

    private static UserServiceImpl userServiceImpl;

    public static UserServiceImpl getInstance() {
        if(userServiceImpl == null) {
            userServiceImpl = new UserServiceImpl();
        }
        return userServiceImpl;
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users saveUser(Users users) {
         return userRepository.save(users);
    }
}*/
