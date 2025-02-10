package com.example.CoutingStarHotel.repositories;

import com.example.CoutingStarHotel.entities.BookedRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {
    @Query("SELECT br FROM BookedRoom br WHERE br.room.id = :roomId")
    List<BookedRoom> findByRoomId(Long roomId);

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);

    Page<BookedRoom> findByUserId(Pageable pageable, Long userId);

    @Query("SELECT br FROM BookedRoom br JOIN br.room r JOIN r.hotel h WHERE h.id = :hotelId")
    Page<BookedRoom> findByHotelId(Pageable pageable, Long hotelId);

    @Query("SELECT br FROM BookedRoom br LEFT JOIN br.rating r " +
            "WHERE br.user.id = :userId " +
            "AND br.room.hotel.id = :hotelId " +
            "AND r.id IS NULL")
    List<BookedRoom> findRoomUserHasBookedAndNotComment(Long hotelId, Long userId, Pageable pageable);

    @Query("SELECT COUNT(b) FROM BookedRoom b")
    int getTotalNumberOfBookedRooms();

    @Query("SELECT COUNT(b) FROM BookedRoom b WHERE b.bookingDay >= :startDate AND b.bookingDay < :endDate")
    int getBookedRoomsAddedDuringPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM BookedRoom b WHERE lower(b.bookingConfirmationCode) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(b.guestEmail) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(b.guestFullName) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(b.guestPhoneNumber) LIKE lower(concat('%', :keyword, '%')) OR " +
            "cast(b.id as string) LIKE lower(concat('%', :keyword, '%')) AND " +
            "b.room.hotel.id = :hotelId")
    Page<BookedRoom> getAllBookingByKeywordAndHotelId(Pageable pageable, Long hotelId, String keyword);
}
