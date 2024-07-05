package com.example.CoutingStarHotel.repository;

import com.example.CoutingStarHotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% " +
            "AND r.id NOT IN ( " +
            "SELECT br.room.id FROM BookedRoom br " +
            "WHERE (br.checkInDate <= :checkOutDate AND br.checkOutDate >= :checkInDate))")
    List<Room> findAvailableRoomsByDatesAndType(@Param("checkInDate") LocalDate checkInDate,
                                                @Param("checkOutDate") LocalDate checkOutDate,
                                                @Param("roomType") String roomType);

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId")
    List<Room> findRoomsByHotelId(@Param("hotelId") Long hotelId);
}
