package com.evgeniy.riakhin.backend.repository;

import com.evgeniy.riakhin.backend.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u")
    List<User> getAllUsers();

    @Query("select u from User u where u.name = :username")
    User findByUserName(String username);
}
