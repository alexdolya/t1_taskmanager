package ru.dolya.t1_taskmanager.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dolya.t1_taskmanager.exception.TaskNotFoundException;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleEntityNotExistsException(TaskNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleEntityNotExistsException(ConstraintViolationException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}