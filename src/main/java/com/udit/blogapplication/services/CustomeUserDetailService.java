package com.udit.blogapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udit.blogapplication.entities.CustomUserDetail;
import com.udit.blogapplication.entities.User;
import com.udit.blogapplication.repository.UserRepository;

@Service
public class CustomeUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user= this.userRepository.findByUsername(username);
       if(user==null){
        throw new UsernameNotFoundException("No User found");
       } 
      
      return new CustomUserDetail(user);
    }
    
}
