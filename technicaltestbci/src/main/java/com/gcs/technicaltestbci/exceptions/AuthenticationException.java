package com.gcs.technicaltestbci.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends BaseExceptions{

public AuthenticationException(String message){
    super(HttpStatus.UNAUTHORIZED, message);
}
    
}
