package com.example.CountingStarHotel.services.impl.helpers;

import com.example.CountingStarHotel.entities.Hotel;
import com.example.CountingStarHotel.repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelCoordinator {
    HotelRepository hotelRepository;

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
    }
}
