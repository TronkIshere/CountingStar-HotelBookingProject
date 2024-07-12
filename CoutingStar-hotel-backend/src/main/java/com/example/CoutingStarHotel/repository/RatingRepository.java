package com.example.CoutingStarHotel.repository;

import com.example.CoutingStarHotel.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r " +
            "JOIN r.bookedRoom br " +
            "JOIN br.room ro " +
            "JOIN ro.hotel h " +
            "WHERE h.id = :hotelId")
    List<Rating> getAllRatingByHotelId(Long hotelId);
}
