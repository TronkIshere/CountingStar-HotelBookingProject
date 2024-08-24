package com.example.CoutingStarHotel.repositories;

import com.example.CoutingStarHotel.entities.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomId(Long roomId);

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByUserId(Long userId);

    @Query("SELECT br FROM BookedRoom br JOIN br.room r JOIN r.hotel h WHERE h.id = :hotelId")
    List<BookedRoom> findByHotelId(Long hotelId);

    @Query("SELECT COUNT(br) > 0 FROM BookedRoom br WHERE br.user.id = :userId AND br.room.hotel.id = :hotelId")
    boolean hasBookedRoom(Long userId, Long hotelId);

    @Query("SELECT COUNT(r) > 0 FROM Rating r WHERE r.user.id = :userId AND r.bookedRoom.room.hotel.id = :hotelId")
    boolean hasCommented(Long userId, Long hotelId);

    @Query("SELECT MIN(br.bookingId) FROM BookedRoom br LEFT JOIN br.rating r " +
            "WHERE br.user.id = :userId " +
            "AND br.room.hotel.id = :hotelId " +
            "AND r.id IS NULL")
    Long findRoomUserHasBookedAndNotComment(Long hotelId, Long userId);
}
