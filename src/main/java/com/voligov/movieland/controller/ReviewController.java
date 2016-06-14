package com.voligov.movieland.controller;

import com.voligov.movieland.entity.Review;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.util.JsonConverter;
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
    private JsonConverter jsonConverter;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addReview(@RequestHeader("token") String token, @RequestBody String json) {
        Review review = jsonConverter.parseReview(json);
        if (reviewService.add(review, token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteReview(@RequestHeader("token") String token, @RequestBody String json) {
        Review review = jsonConverter.parseReview(json);
        if (reviewService.delete(review, token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
