package org.example.revizor.audit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message){
        super(message);
    }
}