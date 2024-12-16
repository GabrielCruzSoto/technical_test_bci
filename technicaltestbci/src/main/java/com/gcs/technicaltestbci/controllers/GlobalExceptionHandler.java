package com.gcs.technicaltestbci.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.gcs.technicaltestbci.exceptions.BaseExceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackages = "com.gcs.technicaltestbci") 
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseExceptions.class)
    public ResponseEntity<?> managerBaseException(BaseExceptions ex, WebRequest request) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(hashMap, ex.getHttpStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> managerException(Exception ex, WebRequest request) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("mensaje", "Se produjo un error inesperado. Vuelva a intentarlo más tarde.");
        log.error("managerException| ex.message={}", ex.getMessage());
        log.error("managerException| ex.localizedMessage={}", ex.getLocalizedMessage());
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        log.error("managerException| ex.getCause={}", ex.getCause().getMessage());
        log.error("managerException| ex.stackTrace={}", sb.toString());
        return new ResponseEntity<>(hashMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
