package com.weather.controller;


import com.weather.config.JWTUtil;
import com.weather.model.JwtRequest;
import com.weather.model.JwtResponse;
import com.weather.service.StandardUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authenticate")
public class StandardUserAuthController {

    @Autowired
    private StandardUserDetailService userDetailService;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest request)
    {
        authenticateUser(request.getUsername(),request.getPassword());

        final UserDetails userDetails= userDetailService.loadUserByUsername(request.getUsername());
        final String token= jwtUtil.generateToken(userDetails);


        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticateUser(String username, String password) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }
}
