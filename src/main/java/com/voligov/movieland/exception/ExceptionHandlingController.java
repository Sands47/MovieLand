package com.voligov.movieland.exception;

import com.voligov.movieland.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @Autowired
    private JsonConverter jsonConverter;

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> securityException(SecurityException e) {
        return new ResponseEntity<>(jsonConverter.wrapError(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}