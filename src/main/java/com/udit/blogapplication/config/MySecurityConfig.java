package com.udit.blogapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.udit.blogapplication.services.CustomeUserDetailService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
  
    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Autowired
   private CustomeUserDetailService customeUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
        cors().disable().
        authorizeRequests()
        .antMatchers("/","/login","/register","/createUser","/filter","/search","/sort","/page/**","/filter/search","sortByDate/{pageNumber}","sortByDate","/token").permitAll()
        .anyRequest()
        .authenticated()
        .and()
       // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/doLogin")
        .defaultSuccessUrl("/")
        .and()
        .logout()
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/access-denied")
        ;

        http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customeUserDetailService).passwordEncoder(passwordEncoder());
        }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }



    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
   return super.authenticationManagerBean();
    }
    
    
}
