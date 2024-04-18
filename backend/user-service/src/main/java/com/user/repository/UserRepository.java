package com.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.user.entity.User;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, String>{
    
    Optional<User> findById(String id);
    Iterable<User> findAll();
    void deleteById(String id);

}
