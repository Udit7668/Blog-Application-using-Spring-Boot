package com.udit.blogapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.udit.blogapplication.services.CustomeUserDetailService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
   private CustomeUserDetailService customeUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
        authorizeRequests()
        .antMatchers("/","/login","/register","/createUser","/filter","/search","/sort","/page/**","/filter/search").permitAll()
        
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/doLogin")
        .defaultSuccessUrl("/")
        .and()
        .logout()
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/access-denied");

    
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customeUserDetailService).passwordEncoder(passwordEncoder());
        }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    
    
}
