package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.Hotel;
import com.example.CoutingStarHotel.model.User;
import com.example.CoutingStarHotel.repository.HotelRepository;
import com.example.CoutingStarHotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService implements IHotelService{
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Override
    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    @Override
    public String saveHotel(Long userId, Hotel hotelRequest){
        User user = userRepository.getById(userId);
        user.addHotel(hotelRequest);
        hotelRepository.save(hotelRequest);
        return user.getLastName();
    }

    @Override
    public void deleteHotel(Long hotelId){
        hotelRepository.deleteById(hotelId);
    }
}
