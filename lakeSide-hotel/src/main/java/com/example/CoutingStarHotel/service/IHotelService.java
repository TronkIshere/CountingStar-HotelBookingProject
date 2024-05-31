package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.Hotel;

import java.util.List;

public interface IHotelService {
    List<Hotel> getAllHotels();

    String saveHotel(Long userId, Hotel hotelRequest);

    void deleteHotel(Long hotelId);
}
