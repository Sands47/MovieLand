package com.voligov.movieland.controller;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.service.UserService;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.entity.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.voligov.movieland.util.Constant.TOKEN;
import static com.voligov.movieland.util.Constant.V1_PATH;

@Controller
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> authorizeUser(@RequestBody String json, HttpServletResponse response) {
        UserCredentials credentials = jsonConverter.parseUserCredentials(json);
        if (credentials.isInvalid()) {
            return new ResponseEntity<>(jsonConverter.wrapError("User credentials are invalid"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUser(credentials.getLogin());
        if (user == null || !securityService.validateUser(credentials, user)) {
            return new ResponseEntity<>(jsonConverter.wrapError("Login or password are invalid"), HttpStatus.BAD_REQUEST);
        }
        String token = securityService.registerUser(user).getToken();
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setMaxAge(7200);
        cookie.setPath(V1_PATH);
        response.addCookie(cookie);
        return new ResponseEntity<>(jsonConverter.wrapResponse(token), HttpStatus.OK);
    }
}
