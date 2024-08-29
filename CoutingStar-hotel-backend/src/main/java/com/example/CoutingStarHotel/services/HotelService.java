package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.entities.Rating;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.repositories.HotelRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.impl.HotelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService implements HotelServiceImpl {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final RatingService ratingService;

    @Override
    public String addHotel(Long userId,
                           String hotelName,
                           String city,
                           String hotelLocation,
                           String hotelDescription,
                           String phoneNumber,
                           MultipartFile photo) throws IOException, SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setCity(city);
        hotel.setHotelLocation(hotelLocation);
        hotel.setHotelDescription(hotelDescription);
        hotel.setPhoneNumber(phoneNumber);
        if(!photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            hotel.setPhoto(photoBlob);
        }

        User user = userRepository.findById(userId).get();
        user.addHotel(hotel);
        hotelRepository.save(hotel);
        return user.getLastName();
    }

    @Override
    public Optional<Hotel> getHotelById(Long hotelId) {
        return Optional.ofNullable(hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId)));
    }

    @Override
    public double averageNumberOfHotelStars(Long hotelId){
        double result = 0;
        int count = 0;
        List<Rating> ratings = ratingService.getAllRatingByHotelId(hotelId);
        for(Rating rating: ratings){
            result += rating.getStar();
            count++;
        }
        return result/count;
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
    public List<Hotel> getFiveHotelForHomePage() {
        return hotelRepository.getFiveHotelForHomePage().stream().limit(5).collect(Collectors.toList());
    }

    @Override
    public List<PieChartDTO> getNumberOfHotelByEachCity() {
        List<PieChartDTO> numberOfHotelByEachCity = hotelRepository.findNumberOfHotelsByCity();
        return numberOfHotelByEachCity;
    }

    @Override
    public List<BarChartDTO> getHotelRevenueByEachCity() {
        List<BarChartDTO> revenueByEachCity = hotelRepository.findRevenueByEachCity();
        return revenueByEachCity;
    }

    @Override
    public int getTotalNumberOfHotels() {
        return hotelRepository.getTotalNumberOfHotels();
    }

    @Override
    public double getPercentageOfHotelsIncreasedDuringTheMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        int totalHotels = hotelRepository.getTotalNumberOfHotels();
        int hotelsAddedThisMonth = hotelRepository.getHotelsAddedDuringPeriod(firstDayOfThisMonth, firstDayOfNextMonth);

        return (hotelsAddedThisMonth * 100.0) / totalHotels;
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
