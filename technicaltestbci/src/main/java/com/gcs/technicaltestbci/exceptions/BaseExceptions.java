package com.gcs.technicaltestbci.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BaseExceptions extends Exception{
    private final HttpStatus httpStatus;

    public BaseExceptions(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
