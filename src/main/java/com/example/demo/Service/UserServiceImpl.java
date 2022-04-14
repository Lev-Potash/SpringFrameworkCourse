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
 * Логин пользователя можно получить из объекта <code>SecurityContextHolder.getContext().getAuthentication()</code>.
 * Далее, достаем пользователя по логину с БД
 *
 * @see Service
 * Мы помечаем бобы <code>@Service</code>, чтобы указать, что они содержат бизнес-логику.
 * Помимо использования на сервисном уровне, для этой аннотации нет другого специального использования.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * @see Autowired
     * Внедряет единственный экземпляр (в данном случае) класса в поле userRepository из компонента <code>@Repository</code>
     * @see UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Autowired
   // private UserDetailsServiceImpl userDetailsService;

    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users create(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * <code>authentication.getPrincipal()</code>
     * @return <code>Principal</code> (участника), который проходит проверку подлинности,
     * или участника, прошедшего проверку подлинности после проверки подлинности.
     *
     * <code>principal</code> объект класса <code>org.springframework.security.core.userdetails.User</code>,
     * у которого при помощи метода <code>principal.getUsername()</code> выдается Логин пользователя.
     * Наш метод вернет Логин пользователя, если он есть в репозитории, иначе исключение от Optional<>
     */
    @Override
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        System.out.println("authentication.getDetails()egdgdgdgeg \n" + authentication.getDetails());
       return userRepository.findByLogin(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        //return userDetailsService.loadUserByUsername(principal.getUsername()).getUsername();
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
