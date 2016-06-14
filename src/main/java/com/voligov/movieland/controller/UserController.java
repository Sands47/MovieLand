package com.voligov.movieland.controller;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.entity.UserToken;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> authorizeUser(@RequestBody String json) {
        UserCredentials credentials = jsonConverter.parseUserCredentials(json);
        if (credentials.isInvalid()) {
            return new ResponseEntity<>("User credentials received are incomplete", HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUser(credentials);
        if (user == null) {
            return new ResponseEntity<>("User not found in database", HttpStatus.BAD_REQUEST);
        }
        if (!userService.validateUser(credentials, user)) {
            return new ResponseEntity<>("User password is invalid", HttpStatus.BAD_REQUEST);
        }
        UserToken token = userService.generateToken(user);
        return new ResponseEntity<>(token.getToken().toString(), HttpStatus.OK);
    }
}
