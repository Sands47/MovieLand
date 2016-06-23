package com.voligov.movieland.controller;

import com.voligov.movieland.controller.annotation.RoleRequired;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.UserToken;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private JsonConverter jsonConverter;

    @RoleRequired(role = UserRole.USER)
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> addReview(@RequestHeader("token") String token, @RequestBody String json) {
        Review review = jsonConverter.parseReview(json);
        UserToken userToken = securityService.validateToken(token);
        if (reviewService.add(review, userToken.getUser())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(jsonConverter.wrapError("You already have a review for this movie"), HttpStatus.BAD_REQUEST);
        }
    }

    @RoleRequired(role = UserRole.USER)
    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> deleteReview(@RequestHeader("token") String token, @RequestBody String json) {
        Review review = jsonConverter.parseReview(json);
        UserToken userToken = securityService.validateToken(token);
        if (reviewService.delete(review, userToken.getUser())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(jsonConverter.wrapError("Review doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }
}
