package com.example.CountingStarHotel.repositories;

import com.example.CountingStarHotel.DTO.response.dashBoard.BarChartResponse;
import com.example.CountingStarHotel.DTO.response.dashBoard.PieChartResponse;
import com.example.CountingStarHotel.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT distinct h FROM Hotel h WHERE h.city = :city")
    Page<Hotel> findAllHotelsByCity(@Param("city") String city, Pageable pageable);

    @Query("SELECT MIN(r.roomPrice) FROM Room r WHERE r.hotel.id = :hotelId")
    Long getHotelLowestPriceByHotelId(Long hotelId);

    @Query("SELECT MAX(r.roomPrice) FROM Room r WHERE r.hotel.id = :hotelId")
    Long getHotelHighestPriceByHotelId(Long hotelId);

    @Query("SELECT h FROM Hotel h LEFT JOIN h.rooms r LEFT JOIN r.bookings b LEFT JOIN b.ratings rt GROUP BY h ORDER BY COUNT(rt) DESC")
    List<Hotel> getTenHotelForHomePage();

    @Query("SELECT PieChartResponse(h.city, COUNT(h)) " +
            "FROM Hotel h " +
            "GROUP BY h.city")
    List<PieChartResponse> findNumberOfHotelsByCity();

    @Query("SELECT new com.example.CountingStarHotel.DTO.response.dashBoard.BarChartResponse(h.city, SUM(b.totalAmount)) " +
            "FROM Hotel h JOIN h.rooms r JOIN r.bookings b " +
            "GROUP BY h.city")
    List<BarChartResponse> findRevenueByEachCity();

    @Query("SELECT COUNT(h) FROM Hotel h")
    int getTotalNumberOfHotels();

    @Query("SELECT COUNT(h) FROM Hotel h WHERE h.registerDay >= :startDate AND h.registerDay < :endDate")
    int getHotelsAddedDuringPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT PieChartResponse(r.roomType, SUM(b.totalAmount)) " +
            "FROM Room r JOIN r.bookings b " +
            "WHERE r.hotel.id = :hotelId " +
            "GROUP BY r.roomType")
    List<PieChartResponse> findRevenueByEachRoom(Long hotelId);

    @Query("SELECT COUNT(b) FROM BookedRoom b WHERE b.room.hotel.id = :hotelId")
    int getTotalBookedRoomInSpecificHotel(@Param("hotelId")Long hotelId);

    @Query("SELECT COUNT(b) FROM BookedRoom b WHERE b.room.hotel.id = :hotelId AND b.bookingDay >= :firstDayOfThisMonth AND b.bookingDay < :firstDayOfNextMonth")
    int getHotelsBookedDuringPeriod(@Param("hotelId")Long hotelId,@Param("firstDayOfThisMonth") LocalDate firstDayOfThisMonth,@Param("firstDayOfNextMonth") LocalDate firstDayOfNextMonth);

    @Query("SELECT SUM(b.totalAmount) FROM BookedRoom b WHERE b.room.hotel.id = :hotelId")
    BigDecimal getTotalRevenueInSpecificHotel(@Param("hotelId")Long hotelId);

    @Query("SELECT SUM(b.totalAmount) FROM BookedRoom b WHERE b.room.hotel.id = :hotelId AND b.bookingDay >= :firstDayOfThisMonth AND b.bookingDay < :firstDayOfNextMonth")
    BigDecimal getHotelRevenueDuringPeriod(@Param("hotelId")Long hotelId,@Param("firstDayOfThisMonth") LocalDate firstDayOfThisMonth,@Param("firstDayOfNextMonth") LocalDate firstDayOfNextMonth);

    @Query("SELECT h FROM Hotel h WHERE lower(h.city) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(h.description) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(h.address) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(h.name) LIKE lower(concat('%', :keyword, '%')) OR " +
            "cast(h.id as string) LIKE lower(concat('%', :keyword, '%')) OR " +
            "cast(h.phoneNumber as string) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(h.user.firstName) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(h.user.lastName) LIKE lower(concat('%', :keyword, '%'))")
    Page<Hotel> getHotelByKeyword(Pageable pageable, String keyword);

    @Query("SELECT h FROM Hotel h WHERE h.name = :name")
    Hotel findByHotelName(String name);
}
