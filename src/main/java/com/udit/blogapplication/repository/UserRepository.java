package com.udit.blogapplication.repository;

import org.springframework.data.repository.CrudRepository;

import com.udit.blogapplication.entities.User;

public interface UserRepository extends CrudRepository<User,Integer>{
    

    public User findByUsername(String user);
}
