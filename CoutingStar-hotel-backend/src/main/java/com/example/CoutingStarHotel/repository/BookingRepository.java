package com.example.CoutingStarHotel.repository;

import com.example.CoutingStarHotel.model.BookedRoom;
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
}
