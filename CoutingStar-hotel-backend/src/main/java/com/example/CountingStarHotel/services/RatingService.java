package com.example.CountingStarHotel.services;

import com.example.CountingStarHotel.DTO.request.rating.AddRatingRequest;
import com.example.CountingStarHotel.DTO.request.rating.UpdateRatingRequest;
import com.example.CountingStarHotel.DTO.response.rating.RatingResponse;
import com.example.CountingStarHotel.entities.Rating;

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
