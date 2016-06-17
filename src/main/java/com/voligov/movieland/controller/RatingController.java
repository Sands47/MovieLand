package com.voligov.movieland.controller;

import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.service.RatingService;
import com.voligov.movieland.util.JsonConverter;
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

    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> rate(@RequestHeader("token") String token, @RequestBody String json) {
        try {
            Rating rating = jsonConverter.parseRating(json);
            if (ratingService.add(rating, token)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(jsonConverter.wrapResponse("Rating must be between 0 and 10"), HttpStatus.BAD_REQUEST);
            }
        } catch (SecurityException e) {
            return new ResponseEntity<>(jsonConverter.wrapResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
