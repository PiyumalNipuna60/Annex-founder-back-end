package com.example.annex_founder.controller;

import com.example.annex_founder.dto.ReviewDto;
import com.example.annex_founder.dto.get.ReviewDtoGet;
import com.example.annex_founder.service.ReviewService;
import com.example.annex_founder.util.JWTTokenGenerator;
import com.example.annex_founder.util.TokenStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final JWTTokenGenerator jwtTokenGenerator;
    private final ReviewService reviewService;

    public ReviewController(JWTTokenGenerator jwtTokenGenerator, ReviewService reviewService) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Object> saveReviews(@RequestBody ReviewDto reviewDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            ReviewDto dto = this.reviewService.saveHouseRenter(reviewDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllReviews(@RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            List<ReviewDtoGet> dtoList = this.reviewService.getAllHouseRenter();
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Object> searchReviews(@PathVariable Long reviewId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            ReviewDtoGet dto = this.reviewService.searchHouseRenter(reviewId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Object> updateReviews(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            ReviewDto dto = this.reviewService.updateHouseRenter(reviewId, reviewDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Object> deleteReviews(@PathVariable Long reviewId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            ReviewDto dto = this.reviewService.deleteHouseRenter(reviewId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }
}
