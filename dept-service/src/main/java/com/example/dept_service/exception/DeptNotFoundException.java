package com.example.dept_service.exception;

public class DeptNotFoundException extends RuntimeException {
    public DeptNotFoundException(String message){
        super(message);
    }
}
