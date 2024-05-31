package com.example.CoutingStarHotel.repository;

import com.example.CoutingStarHotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
