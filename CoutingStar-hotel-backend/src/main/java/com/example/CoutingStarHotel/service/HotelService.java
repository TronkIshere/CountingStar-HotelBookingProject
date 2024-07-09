package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.exception.InternalServerException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.model.Hotel;
import com.example.CoutingStarHotel.model.User;
import com.example.CoutingStarHotel.repository.HotelRepository;
import com.example.CoutingStarHotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService implements IHotelService{
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Override
    public String addHotel(Long userId,
                           String hotelName,
                           String city,
                           String hotelDescription,
                           String phoneNumber,
                           MultipartFile photo) throws IOException, SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setCity(city);
        hotel.setHotelDescription(hotelDescription);
        hotel.setPhoneNumber(phoneNumber);
        if(!photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            hotel.setPhoto(photoBlob);
        }

        User user = userRepository.findById(userId).get();
        user.addHotel(hotel);

        return user.getLastName();
    }

    @Override
    public Optional<Hotel> getHotelById(Long hotelId) {
        return Optional.ofNullable(hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId)));
    }

    @Override
    public Long getHotelLowestPriceByHotelId(Long hotelId) {
        return hotelRepository.getHotelLowestPriceByHotelId(hotelId);
    }

    @Override
    public Long getHotelHighestPriceByHotelId(Long hotelId) {
        return hotelRepository.getHotelHighestPriceByHotelId(hotelId);
    }
    @Override
    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> getAllHotelsByCity(String city){
        return hotelRepository.findAllHotelsByCity(city);
    }

    @Override
    public byte[] getHotelPhotobyHotelId(Long hotelId) throws SQLException {
        Optional<Hotel> theHotel = hotelRepository.findById(hotelId);
        if(theHotel.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Hotel not found");
        }
        Blob photoBlob = theHotel.get().getPhoto();
        if(photoBlob != null){
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public Hotel updateHotel(Long hotelId, String hotelName, String hotelLocation, String hotelDescription, String phoneNumber, String city, MultipartFile photo) throws IOException, SQLException {
        Hotel hotel = hotelRepository.findById(hotelId).get();
        if (hotelName != null) hotel.setHotelName(hotelName);
        if (hotelLocation != null) hotel.setHotelLocation(hotelLocation);
        if (hotelDescription != null) hotel.setHotelDescription(hotelDescription);
        if (phoneNumber != null) hotel.setPhoneNumber(phoneNumber);
        if (city != null) hotel.setCity(city);
        if (photo != null && !photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            hotel.setPhoto(photoBlob);
        }
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long hotelId){
        hotelRepository.deleteById(hotelId);
    }


}
