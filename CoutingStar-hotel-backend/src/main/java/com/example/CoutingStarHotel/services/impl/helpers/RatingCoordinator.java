package com.example.CoutingStarHotel.services.impl.helpers;

import com.example.CoutingStarHotel.entities.Rating;
import com.example.CoutingStarHotel.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingCoordinator {
    private final RatingRepository repository;

    public List<Rating> getAllRatingByRoomId(Long roomId) {
        return repository.getAllRatingByRoomId(roomId);
    }
}
