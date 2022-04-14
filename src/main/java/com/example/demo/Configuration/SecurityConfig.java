package com.example.demo.Configuration;


import com.example.demo.handler.CustomAuthenticationFailureHandler;
import com.example.demo.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Аннотация @EnableWebSecurity в связке с WebSecurityConfigurerAdapter классом работает над обеспечением аутентификации.
 * По умолчанию в Spring Security встроены и активны HTTP аутентификация и аутентификация на базе веб форм.
 * В переопределенном методе
 * @see #configure(HttpSecurity http) можно также пререопределить доступ к кронечным точкам в зависимости от роли пользователя.
 *
 * <code>
 * http.authorizeRequests()
 *       .antMatchers("/admin/**")
 *       .hasRole("ADMIN")
 *       .antMatchers("/protected/**")
 *       .hasRole("USER");
 * </code>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    /**
     * .antMatchers("/login*").permitAll() - таким образом можно указать все ресты, которые можно вызывать без авторизации.
     * .formLogin().loginProcessingUrl("/login") - тут указывается рест для авторизации.
     * .anyRequest().authenticated() - любой другой запрос (<b>http.authorizeRequests()</b>), который мы получаем аутентифицирутся
     * .usernameParameter("login").passwordParameter("password") - указаны параметры post запроса для авторизации.
     * .failureHandler(customAuthenticationFailureHandler) - указываем ранее созданный failureHandler.
     * .successHandler(customAuthenticationSuccessHandler) - указываем ранее созданный successHandler.
     * PasswordEncoder - используется для хэширования пароля
     * DaoAuthenticationProvider - тут мы указываем UserDetailsService и PasswordEncoder
     * @param http использует HttpSecurity
     * @throws Exception исключение
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // доступ разрешен всем пользователям
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                // доступ разрешен пользователям с ролью USER
                .antMatchers(HttpMethod.GET,"/main-user").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/main-admin").hasAuthority("ADMIN")
                //.antMatchers("/users/**").hasAuthority("USER")
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .usernameParameter("login").passwordParameter("password")
                //Перенарпавление на главную страницу после успешного входа
                //.defaultSuccessUrl("/users/me/")
                .failureHandler(customAuthenticationFailureHandler)
                .successHandler(customAuthenticationSuccessHandler)
                .and().csrf().disable()
                .sessionManagement()
        ;

    }

    /**
     * PasswordEncoder - используется для хэширования пароля
     * @see Bean - это аннотация, которая позволяет указывать на экземпляр класса, созданного в методе
     * @see #passwordEncoder(). Таким образом мы получаем экземпляр класса бина (<b>new BCryptPasswordEncoder()</b>).
     * Bean указывет только на метод, который возращает экземпляр бина нужного нам класса
     * @see BCryptPasswordEncoder() - это констуктор одноименного класса BCryptPasswordEncoder, который принимает значение
     * strenght ("силы") для настройки уровня шифрования. Важно подобрать в констуктор такие параметры, чтобы отлик на аутентифиацию
     * Логина и пароля пользователя соответсвовал 1 секунде.
     * BCrypt - один из стандартов шифрования (таких как: SCrypt, sha256, pbkdf2, noop и др)
     *
     * @return BCryptPasswordEncoder()
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * DaoAuthenticationProvider - тут мы указываем UserDetailsService и PasswordEncoder
     * @return authProvider
     */
    @Bean
   public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authProvider());
    }

}
