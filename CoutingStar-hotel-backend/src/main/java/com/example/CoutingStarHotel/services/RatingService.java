package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Rating;

import java.time.LocalDate;
import java.util.List;

public interface RatingService {
    Rating saveRating(Long hotelId, Long userId, int star, String comment, LocalDate rateDay);

    Rating updateRating(Long ratingId, int star, String comment);

    void deleteRating(Long ratingId);

    String checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId);

    List<Rating> getAllRatingByHotelId(Long hotelId);

    List<Rating> getAllRatingByRoomId(Long roomId);

    int getTotalNumberOfComments();

    double getPercentageOfCommentsIncreaseDuringTheMonth();

    int getTotalRatingInSpecificHotel(Long hotelId);

    double getPercentageOfRatingIncreasedDuringTheMonthForHotel(Long hotelId);
}
