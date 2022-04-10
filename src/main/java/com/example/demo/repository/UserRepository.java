package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Users, Long> {

    Optional<Users> findByLogin(String login);

}
