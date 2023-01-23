package com.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class StandardUserDetailService implements UserDetailsService {

   @Autowired
   private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.isEmpty() && username.equals("appUser"))
        {
            return new User(username,"$2a$10$n5DKyUZPDgH5c4VI12DCrOA.v/9Z5goUiBZMimddJAm/RgP.m94UW", new ArrayList<>());
        }

        throw new UsernameNotFoundException("User not found user username :"+username);

    }
}
