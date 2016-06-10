package com.voligov.movieland.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such movie found in database")
public class MovieNotFoundException extends RuntimeException {
}
