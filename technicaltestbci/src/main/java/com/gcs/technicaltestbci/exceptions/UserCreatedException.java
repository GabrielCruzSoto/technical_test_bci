package com.gcs.technicaltestbci.exceptions;

import org.springframework.http.HttpStatus;

public class UserCreatedException extends BaseExceptions {

    public UserCreatedException() {
        super(HttpStatus.CONFLICT, "User already exists");
    }
    
}
