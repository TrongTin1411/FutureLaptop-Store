package me.trongtin.project.controller;

import me.trongtin.project.exception.AlreadyExistsException;
import me.trongtin.project.exception.ResourceNotFoundException;
import me.trongtin.project.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyExists(AlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
