package com.udit.blogapplication.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udit.blogapplication.entities.User;
import com.udit.blogapplication.helper.JwtUtil;
import com.udit.blogapplication.services.CustomeUserDetailService;

@RestController
public class JwtController {
    

    @Autowired
    private AuthenticationManager authenticationManager;
   
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomeUserDetailService customeUserDetailService;
   
   
    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody User user){

        System.out.println(user);
    }
}
