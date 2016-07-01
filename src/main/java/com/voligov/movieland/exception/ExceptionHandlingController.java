package com.voligov.movieland.exception;

import com.voligov.movieland.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JsonConverter jsonConverter;

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> securityException(SecurityException e) {
        return new ResponseEntity<>(jsonConverter.wrapError(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> generalException(Exception e) {
        log.warn("Unhandled exception thrown:", e);
        return new ResponseEntity<>(jsonConverter.wrapError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}