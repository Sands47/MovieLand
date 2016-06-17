package com.voligov.movieland.controller;

import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.service.UserService;
import com.voligov.movieland.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> authorizeUser(@RequestBody String json) {
        try {
            UserCredentials credentials = jsonConverter.parseUserCredentials(json);
            String token = userService.authoriseUser(credentials);
            return new ResponseEntity<>(jsonConverter.wrapResponse(token), HttpStatus.OK);
        } catch (SecurityException e) {
            return new ResponseEntity<>(jsonConverter.wrapResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
