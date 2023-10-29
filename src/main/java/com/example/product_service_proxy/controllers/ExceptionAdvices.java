package com.example.product_service_proxy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionAdvices {
    @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class})
    public ResponseEntity<String> handleException(Exception e){
        return new ResponseEntity<>("Printing error message from seperate exception handler", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
