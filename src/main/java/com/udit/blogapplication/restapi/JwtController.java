package com.udit.blogapplication.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udit.blogapplication.entities.JwtResponse;
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
    public ResponseEntity<?> generateToken(@RequestBody User user) throws Exception{

        System.out.println(user);
       
        try{
       this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        }
        catch(UsernameNotFoundException e){
        e.printStackTrace();
        throw new Exception("Bad Credentials");
        }
        catch(BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("Bad Credetials");
        }

       UserDetails userDetails= this.customeUserDetailService.loadUserByUsername(user.getUsername());

      String token= this.jwtUtil.generateToken(userDetails);
      System.out.println(token+"************");
    return ResponseEntity.ok(new JwtResponse(token));
    }
}
