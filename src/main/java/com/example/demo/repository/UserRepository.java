package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<Users, Long> {

    /**
     * Именованные параметры
     * Мы также можем передать параметры метода в запрос с помощью именованных параметров. Мы определяем их с помощью аннотации
     * @Param в объявлении метода репозитория.
     *
     * Каждый параметр, аннотированный @Param, должен иметь строку значения, соответствующую соответствующему имени
     * параметра запроса JPQL или SQL.
     * Запрос с именованными параметрами легче читается и менее подвержен ошибкам в случае необходимости рефакторинга запроса.
     *
     * @param login
     */
    @Query("SELECT u FROM Users u " +
            "WHERE u.login = :login")
    Optional<Users> findByLogin(@Param("login") String login);

    /*@Query("SELECT u.id, u.login FROM Users u " +
            "WHERE u.login = :login")
    Optional<Users> findIdLoginByLogin(@Param("login") String login);*/

}
