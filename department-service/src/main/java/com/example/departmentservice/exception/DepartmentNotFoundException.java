package com.example.departmentservice.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String message){
        super(message);
    }
}