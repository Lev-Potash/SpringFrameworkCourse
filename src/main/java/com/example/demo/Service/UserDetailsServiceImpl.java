package com.example.demo.Service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Метод loadUserByUsername получает детали пользователя.
     *
     * Внутри мы использовали UserRepository, который был создан ранее, для регистрации пользователя.
     *
     * В этом репозитории нужно добавить метод findByLogin, который возвращает Optional<User> по логину пользователя
     *
     * В конструкторе
     * <code>
     *     org.springframework.security.core.userdetails.User(
     *          String username,
     *          String password,
     *          Collection<? extends GrantedAuthority> authorities
     * )
     * </code>
     * имеется 3 параметра : username, login, role
     *
     * <code>Collections.singletonList(new SimpleGrantedAuthority("USER")));</code> - по сути добавляет в список объект
     * <code>SimpleGrantedAuthority</code> приравнивая его элементу списка <code>element</code>
     *
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Users user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        System.out.println("user.getLogin() " + user.getLogin());
        //if(user.getLogin() != "admin") {
            return user.getLogin().equals("admin")
                    ?
                    new org.springframework.security.core.userdetails.User(
                            user.getLogin(),
                            user.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority("ADMIN")))
                    :
                    new org.springframework.security.core.userdetails.User(
                            user.getLogin(),
                            user.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority("USER")))

                    ;
        //}

    }



}
