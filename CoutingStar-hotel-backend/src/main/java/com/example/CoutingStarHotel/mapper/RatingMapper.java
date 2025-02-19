package com.example.CoutingStarHotel.mapper;

import com.example.CoutingStarHotel.DTO.response.rating.RatingResponse;
import com.example.CoutingStarHotel.entities.Rating;

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
