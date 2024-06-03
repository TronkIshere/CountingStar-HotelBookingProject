package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.Rating;

import java.util.Date;
import java.util.List;

public interface IRatingService {
    Rating saveRating(Long bookedRoomId, int star, String comment, Date rateDay, Long userId);

    List<Rating> getAllRatingByRoomId(Long roomId);

    Rating updateRating(Long ratingId, int star, String comment);

    void deleteRating(Long ratingId);
}
