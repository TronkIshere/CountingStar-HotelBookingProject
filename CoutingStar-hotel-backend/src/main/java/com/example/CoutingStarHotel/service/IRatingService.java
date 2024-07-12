package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.Rating;

import java.time.LocalDate;
import java.util.List;

public interface IRatingService {
    Rating saveRating(Long hotelId, Long userId, int star, String comment, LocalDate rateDay);

    List<Rating> getAllRatingByRoomId(Long roomId);

    Rating updateRating(Long ratingId, int star, String comment);

    void deleteRating(Long ratingId);

    boolean checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId);
}
