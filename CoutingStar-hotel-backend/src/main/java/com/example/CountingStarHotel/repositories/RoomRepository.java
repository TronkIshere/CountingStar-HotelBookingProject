package com.example.CountingStarHotel.repositories;

import com.example.CountingStarHotel.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId")
    Page<Room> findRoomsByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

    @Query("SELECT r FROM Room r WHERE lower(r.roomType) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(r.roomDescription) LIKE lower(concat('%', :keyword, '%')) AND r.hotel.id = :hotelId")
    Page<Room> getAllRoomByKeywordAndHotelId(Pageable pageable, String keyword, Long hotelId);

    @Query("SELECT r FROM Room r WHERE r.hotel.name = :name")
    Optional<Room> findRoomByHotelName(String name);

    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType")
    Room findByRoomType(String roomType);
}
