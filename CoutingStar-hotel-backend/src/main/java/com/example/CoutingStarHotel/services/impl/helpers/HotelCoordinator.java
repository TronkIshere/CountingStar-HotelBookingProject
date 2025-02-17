package com.example.CoutingStarHotel.services.impl.helpers;

import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelCoordinator {
    private final HotelRepository hotelRepository;

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
    }
}
