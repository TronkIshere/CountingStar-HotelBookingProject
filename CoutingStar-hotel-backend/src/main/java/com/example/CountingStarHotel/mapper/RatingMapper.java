package com.example.CountingStarHotel.mapper;

import com.example.CountingStarHotel.DTO.response.rating.RatingResponse;
import com.example.CountingStarHotel.entity.Rating;

import java.util.List;

public class RatingMapper {
    private RatingMapper() {
    }

    public static RatingResponse toRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .star(rating.getStar())
                .comment(rating.getComment())
                .rateDay(rating.getRateDay())
                .userName(rating.getUser().getFirstName() + rating.getUser().getLastName())
                .roomType(rating.getBookedRoom().getRoom().getRoomType())
                .build();
    }

    public static List<RatingResponse> ratingResponses(List<Rating> ratings) {
        return ratings.stream()
                .map(RatingMapper::toRatingResponse)
                .toList();
    }
}
