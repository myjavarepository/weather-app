package com.weather.config;

import com.weather.service.StandardUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class StandardSecurityConfig extends WebSecurityConfigurerAdapter {


     @Autowired
     private JwtAuthenticationFilter jwtFilter;
     @Autowired
     private JwtAuthEntryPoint jwtAuthEntryPoint;

     @Autowired
     private StandardUserDetailService userDetailService;

     @Bean
     public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
     }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/authenticate/**").permitAll()
        .anyRequest().authenticated().and().sessionManagement().
         sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public  void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
         auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
}
