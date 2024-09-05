package com.example.CoutingStarHotel.repositories;

import com.example.CoutingStarHotel.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r " +
            "JOIN r.bookedRoom br " +
            "JOIN br.room ro " +
            "JOIN ro.hotel h " +
            "WHERE h.id = :hotelId")
    List<Rating> getAllRatingByHotelId(Long hotelId);


    @Query("SELECT r FROM Rating r " +
            "JOIN r.bookedRoom br " +
            "JOIN br.room ro " +
            "WHERE ro.id = :roomId")
    List<Rating> getAllRatingByRoomId(Long roomId);

    @Query("SELECT COUNT(r) FROM Rating r")
    int getTotalNumberOfComments();

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.rateDay >= :startDate AND r.rateDay < :endDate")
    int getUsersAddedDuringPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.bookedRoom.room.hotel.id = :hotelId")
    int getTotalRatingInSpecificHotel(@Param("hotelId") Long hotelId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.bookedRoom.room.hotel.id = :hotelId AND r.rateDay >= :startDate AND r.rateDay < :endDate")
    int getRatingsAddedDuringThisPeriod(@Param("hotelId") Long hotelId,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);
}
