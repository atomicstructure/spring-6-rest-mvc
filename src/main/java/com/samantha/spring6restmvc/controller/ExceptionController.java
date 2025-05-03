package com.samantha.spring6restmvc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFound() {
        System.out.println("Handling Not Found Exception");
        return ResponseEntity.notFound().build();
    }
}
