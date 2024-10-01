package com.example.annex_founder.service;

import com.example.annex_founder.dto.ReviewDto;
import com.example.annex_founder.dto.get.ReviewDtoGet;

import java.util.List;

public interface ReviewService {
    ReviewDto saveHouseRenter(ReviewDto reviewDto);

    List<ReviewDtoGet> getAllHouseRenter();

    ReviewDtoGet searchHouseRenter(Long reviewId);

    ReviewDto updateHouseRenter(Long reviewId, ReviewDto reviewDto);

    ReviewDto deleteHouseRenter(Long reviewId);
}
