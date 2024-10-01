package com.example.annex_founder.service.impl;

import com.example.annex_founder.dto.HouseRenterDto;
import com.example.annex_founder.dto.ReviewDto;
import com.example.annex_founder.dto.get.AnnexDtoGet;
import com.example.annex_founder.dto.get.ReviewDtoGet;
import com.example.annex_founder.model.Annex;
import com.example.annex_founder.model.HouseRenter;
import com.example.annex_founder.model.Review;
import com.example.annex_founder.repo.ReviewRepo;
import com.example.annex_founder.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepo reviewRepo, ModelMapper modelMapper) {
        this.reviewRepo = reviewRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewDto saveHouseRenter(ReviewDto reviewDto) {
        Review review = dtoToEntity(reviewDto);
        Review save = reviewRepo.save(review);
        return entityToReviewDto(save);
    }

    @Override
    public List<ReviewDtoGet> getAllHouseRenter() {
        List<Review> all = reviewRepo.findAll();
        ArrayList<ReviewDtoGet> getAllData = new ArrayList<>();
        for (Review review:all){
            getAllData.add(entityToReviewDtoGet(review));
        }
        return getAllData;
    }

    @Override
    public ReviewDtoGet searchHouseRenter(Long reviewId) {
        Optional<Review> byId = reviewRepo.findById(reviewId);
        return byId.map(this::entityToReviewDtoGet).orElse(null);
    }

    @Override
    public ReviewDto updateHouseRenter(Long reviewId, ReviewDto reviewDto) {
        Optional<Review> byId = reviewRepo.findById(reviewId);
        if (byId.isPresent()) {
            Review review = dtoToEntity(reviewDto);
            review.setReviewId(reviewId);
            Review save = reviewRepo.save(review);
            return entityToReviewDto(save);
        }else {
            return null;
        }
    }

    @Override
    public ReviewDto deleteHouseRenter(Long reviewId) {
        Optional<Review> byId = reviewRepo.findById(reviewId);
        if (byId.isPresent()){
            reviewRepo.deleteById(reviewId);
            return entityToReviewDto(byId.get());
        }else {
            return null;
        }
    }

    private Review dtoToEntity(ReviewDto dto) {
        return modelMapper.map(dto, Review.class);
    }

    private ReviewDto entityToReviewDto(Review save) {
        return modelMapper.map(save, ReviewDto.class);
    }

    private ReviewDtoGet entityToReviewDtoGet(Review save) {
        ReviewDtoGet map = modelMapper.map(save, ReviewDtoGet.class);
        map.setAnnex(entityToAnnexDto(save.getAnnex()));
        return map;
    }

    private AnnexDtoGet entityToAnnexDto(Annex annex) {
        AnnexDtoGet map = modelMapper.map(annex, AnnexDtoGet.class);
        map.setRenterDto(entityToRenterDto(annex.getHouseRenter()));
        return map;
    }

    private HouseRenterDto entityToRenterDto(HouseRenter houseRenter) {
        return modelMapper.map(houseRenter, HouseRenterDto.class);
    }

}
