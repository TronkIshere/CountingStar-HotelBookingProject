package com.example.CoutingStarHotel.repositories;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT distinct h FROM Hotel h WHERE h.city = :city")
    Page<Hotel> findAllHotelsByCity(@Param("city") String city, Pageable pageable);

    @Query("SELECT MIN(r.roomPrice) FROM Room r WHERE r.hotel.id = :hotelId")
    Long getHotelLowestPriceByHotelId(Long hotelId);

    @Query("SELECT MAX(r.roomPrice) FROM Room r WHERE r.hotel.id = :hotelId")
    Long getHotelHighestPriceByHotelId(Long hotelId);

    @Query("SELECT h FROM Hotel h LEFT JOIN h.rooms r LEFT JOIN r.bookings b LEFT JOIN b.rating rt GROUP BY h ORDER BY COUNT(rt) DESC")
    List<Hotel> getFiveHotelForHomePage();

    @Query("SELECT new com.example.CoutingStarHotel.DTO.PieChartDTO(h.city, COUNT(h)) " +
            "FROM Hotel h " +
            "GROUP BY h.city")
    List<PieChartDTO> findNumberOfHotelsByCity();

    @Query("SELECT new com.example.CoutingStarHotel.DTO.BarChartDTO(h.city, SUM(b.totalAmount)) " +
            "FROM Hotel h JOIN h.rooms r JOIN r.bookings b " +
            "GROUP BY h.city")
    List<BarChartDTO> findRevenueByEachCity();

    @Query("SELECT COUNT(h) FROM Hotel h")
    int getTotalNumberOfHotels();

    @Query("SELECT COUNT(h) FROM Hotel h WHERE h.registerDay >= :startDate AND h.registerDay < :endDate")
    int getHotelsAddedDuringPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
