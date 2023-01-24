package com.weather.error;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import java.io.IOException;

@ControllerAdvice
public class StandardExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler (value = {InvalidCityException.class})
    public ResponseEntity<?>  invalidCityException (InvalidCityException ex, WebRequest request)
    {
        return  new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (value = {ExpiredJwtException.class, IOException.class, ServletException.class})
    public ResponseEntity<?>  tokenExpiredException (InvalidCityException ex, WebRequest request)
    {
        return  new ResponseEntity<>("Token is expired or not valid !", HttpStatus.UNAUTHORIZED);
    }
}
