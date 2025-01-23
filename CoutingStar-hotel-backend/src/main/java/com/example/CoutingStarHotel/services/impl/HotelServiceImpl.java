package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.entities.Rating;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.repositories.HotelRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final RatingServiceImpl ratingService;

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
    public List<Hotel> getTenFunkyHotelForHomePage() {
        return hotelRepository.getTenHotelForHomePage().stream().limit(10).collect(Collectors.toList());
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
    public List<PieChartDTO> getTheRevenceOfEachRoom(Long hotelId) {
        List<PieChartDTO> revenueByEachRoom = hotelRepository.findRevenueByEachRoom(hotelId);
        return revenueByEachRoom;
    }

    @Override
    public BigDecimal getTotalRevenueInSpecificHotel(Long hotelId) {
        return hotelRepository.getTotalRevenueInSpecificHotel(hotelId);
    }

    @Override
    public double getPercentageOfRevenueIncreasedDuringTheMonthForHotel(Long hotelId) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        BigDecimal totalRevenue = hotelRepository.getTotalRevenueInSpecificHotel(hotelId);
        BigDecimal revenueIncreasedThisMonth = hotelRepository.getHotelRevenueDuringPeriod(hotelId, firstDayOfThisMonth, firstDayOfNextMonth);

        if (revenueIncreasedThisMonth == null) {
            revenueIncreasedThisMonth = BigDecimal.ZERO;
        }

        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal percentageIncrease = revenueIncreasedThisMonth
                    .multiply(new BigDecimal(100))
                    .divide(totalRevenue, 2, RoundingMode.HALF_UP);

            return percentageIncrease.doubleValue();
        } else {
            return 0.0;
        }
    }

    @Override
    public int getTotalBookedRoomInSpecificHotel(Long hotelId) {
        return hotelRepository.getTotalBookedRoomInSpecificHotel(hotelId);
    }

    @Override
    public double getPercentageOfBookedIncreasedDuringTheMonthForHotel(Long hotelId) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        int totalBookedRooms = hotelRepository.getTotalBookedRoomInSpecificHotel(hotelId);
        int hotelsAddedThisMonth = hotelRepository.getHotelsBookedDuringPeriod(hotelId, firstDayOfThisMonth, firstDayOfNextMonth);

        return (hotelsAddedThisMonth * 100.0) / totalBookedRooms;
    }

    @Override
    public Page<Hotel> getHotelByKeyword(Integer pageNo, Integer pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return hotelRepository.getHotelByKeyword(pageable, keyword);
    }
    @Override
    public Page<Hotel> getAllHotels(Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Page<Hotel> getAllHotelsByCity(String city, Integer pageNo, Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return hotelRepository.findAllHotelsByCity(city, paging);
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
        hotel.setHotelName(hotelName);
        hotel.setHotelLocation(hotelLocation);
        hotel.setHotelDescription(hotelDescription);
        hotel.setPhoneNumber(phoneNumber);
        hotel.setCity(city);
        if (photo != null && !photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            hotel.setPhoto(photoBlob);
        }
        hotel.setRegisterDay(LocalDate.now());
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long hotelId){
        hotelRepository.deleteById(hotelId);
    }


}
