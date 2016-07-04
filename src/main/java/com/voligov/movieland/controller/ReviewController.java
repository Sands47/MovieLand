package com.voligov.movieland.controller;

import com.voligov.movieland.controller.annotation.RoleRequired;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.util.Constant;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/v1/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JsonConverter jsonConverter;

    @RoleRequired(role = UserRole.USER)
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> addReview(@RequestBody String json, HttpServletRequest request) {
        Review review = jsonConverter.parseReview(json);
        User authorizedUser = (User) request.getAttribute(Constant.AUTHORIZED_USER);
        if (!authorizedUser.equals(review.getUser())) {
            return new ResponseEntity<>(jsonConverter.wrapError("You can only add reviews for your own user"), HttpStatus.UNAUTHORIZED);
        }
        if (reviewService.add(review)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(jsonConverter.wrapError("You already have a review for this movie"), HttpStatus.BAD_REQUEST);
        }
    }

    @RoleRequired(role = UserRole.USER)
    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> deleteReview(@RequestBody String json, HttpServletRequest request) {
        Review review = jsonConverter.parseReview(json);
        User authorizedUser = (User) request.getAttribute(Constant.AUTHORIZED_USER);
        if (authorizedUser.getRole() == UserRole.USER) {
            User reviewAuthor = reviewService.getAuthor(review);
            if (reviewAuthor == null) {
                return new ResponseEntity<>(jsonConverter.wrapError("You don't have a review with such Id"), HttpStatus.BAD_REQUEST);
            } else if (!authorizedUser.equals(reviewAuthor)) {
                return new ResponseEntity<>(jsonConverter.wrapError("You can only delete reviews for your own user"), HttpStatus.UNAUTHORIZED);
            }
        }
        reviewService.delete(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
