package com.udit.blogapplication.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udit.blogapplication.entities.User;
import com.udit.blogapplication.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addUser(User user){
        this.userRepository.save(user);
    }
}
