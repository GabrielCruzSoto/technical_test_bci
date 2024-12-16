package com.gcs.technicaltestbci.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseExceptions {
    
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}
