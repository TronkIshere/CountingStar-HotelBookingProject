package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.rating.AddRatingRequest;
import com.example.CoutingStarHotel.DTO.request.rating.UpdateRatingRequest;
import com.example.CoutingStarHotel.DTO.response.rating.RatingResponse;
import com.example.CoutingStarHotel.entities.Rating;

import java.util.List;

public interface RatingService {
    RatingResponse saveRating(Long hotelId, Long userId, AddRatingRequest request);

    RatingResponse updateRating(Long ratingId, UpdateRatingRequest request);

    void deleteRating(Long ratingId);

    String checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId);

    List<RatingResponse> getAllRatingByHotelId(Long hotelId);

    List<Rating> getAllRatingByRoomId(Long roomId);

    int getTotalNumberOfComments();

    double getPercentageOfCommentsIncreaseDuringTheMonth();

    int getTotalRatingInSpecificHotel(Long hotelId);

    double getPercentageOfRatingIncreasedDuringTheMonthForHotel(Long hotelId);

    String softDelete(Long ratingId);
}
