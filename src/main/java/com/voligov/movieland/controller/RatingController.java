package com.voligov.movieland.controller;

import com.voligov.movieland.controller.annotation.RoleRequired;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.service.RatingService;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/rate")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @Autowired
    private JsonConverter jsonConverter;

    @RoleRequired(role = UserRole.USER)
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> rate(@RequestBody String json) {
        Rating rating = jsonConverter.parseRating(json);
        if (rating.getRating() > 10 || rating.getRating() < 0) {
            return new ResponseEntity<>(jsonConverter.wrapError("Rating must be between 0 and 10"), HttpStatus.BAD_REQUEST);
        }
        if (ratingService.add(rating)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(jsonConverter.wrapError("Rating already exists"), HttpStatus.BAD_REQUEST);
        }
    }
}
