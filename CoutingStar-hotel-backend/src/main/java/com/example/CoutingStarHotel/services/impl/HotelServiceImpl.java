package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.request.hotel.AddHotelRequest;
import com.example.CoutingStarHotel.DTO.request.hotel.UpdateHotelRequest;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.DTO.response.dashBoard.BarChartResponse;
import com.example.CoutingStarHotel.DTO.response.dashBoard.PieChartResponse;
import com.example.CoutingStarHotel.DTO.response.hotel.HotelResponse;
import com.example.CoutingStarHotel.DTO.response.rating.RatingResponse;
import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.exception.ApplicationException;
import com.example.CoutingStarHotel.exception.ErrorCode;
import com.example.CoutingStarHotel.mapper.HotelMapper;
import com.example.CoutingStarHotel.repositories.HotelRepository;
import com.example.CoutingStarHotel.services.HotelService;
import com.example.CoutingStarHotel.services.RatingService;
import com.example.CoutingStarHotel.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelServiceImpl implements HotelService {
    HotelRepository hotelRepository;
    UserService userService;
    RatingService ratingService;

    @Override
    public HotelResponse addHotel(Long userId,
                                  AddHotelRequest request) throws IOException, SQLException {
        Hotel hotel = new Hotel();
        hotel.setName(request.getHotelName());
        hotel.setCity(request.getCity());
        hotel.setAddress(request.getHotelLocation());
        hotel.setDescription(request.getHotelDescription());
        hotel.setPhoneNumber(request.getPhoneNumber());
        setHotelPhoto(hotel, request.getPhoto());
        User user = userService.getUserById(userId);
        user.addHotel(hotel);
        hotelRepository.save(hotel);
        return HotelMapper.toHotelResponse(hotel);
    }

    @Override
    public Hotel getHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_HOTEL_REQUEST));
    }

    @Override
    public double averageNumberOfHotelStars(Long hotelId){
        double result = 0;
        int count = 0;
        List<RatingResponse> ratings = ratingService.getAllRatingByHotelId(hotelId);
        for(RatingResponse rating: ratings){
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
    public List<HotelResponse> getTenFunkyHotelForHomePage() {
        List<Hotel> hotelList = hotelRepository.getTenHotelForHomePage().stream().limit(10).collect(Collectors.toList());
        return HotelMapper.hotelResponses(hotelList);
    }

    @Override
    public List<PieChartResponse> getNumberOfHotelByEachCity() {
        return hotelRepository.findNumberOfHotelsByCity();
    }

    @Override
    public List<BarChartResponse> getHotelRevenueByEachCity() {
        return hotelRepository.findRevenueByEachCity();
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
    public List<PieChartResponse> getTheRevenueOfEachRoom(Long hotelId) {
        return hotelRepository.findRevenueByEachRoom(hotelId);
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
    public PageResponse<HotelResponse> getHotelByKeyword(Integer pageNo, Integer pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Hotel> hotelPage = hotelRepository.getHotelByKeyword(pageable, keyword);

        List<Hotel> hotelList = hotelPage.getContent();

        return PageResponse.<HotelResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(hotelPage.getTotalPages())
                .totalElements(hotelPage.getTotalElements())
                .data(HotelMapper.hotelResponses(hotelList))
                .build();
    }

    @Override
    public HotelResponse getHotelResponseById(Long hotelId) {
        return HotelMapper.toHotelResponse(getHotelById(hotelId));
    }

    @Override
    public String softDelete(Long hotelId) {
        LocalDateTime deletedAt = LocalDateTime.now();
        Hotel hotel = getHotelById(hotelId);
        hotel.setDeletedAt(deletedAt);
        hotelRepository.save(hotel);
        return "Hotel with ID " + hotel + " has been deleted at +" + deletedAt;
    }

    @Override
    public PageResponse<HotelResponse> getAllHotels(Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);

        List<Hotel> hotelList = hotelPage.getContent();

        return PageResponse.<HotelResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(hotelPage.getTotalPages())
                .totalElements(hotelPage.getTotalElements())
                .data(HotelMapper.hotelResponses(hotelList))
                .build();
    }

    @Override
    public PageResponse<HotelResponse> getAllHotelsByCity(String city, Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Hotel> hotelPage = hotelRepository.findAllHotelsByCity(city, pageable);

        List<Hotel> hotelList = hotelPage.getContent();

        return PageResponse.<HotelResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(hotelPage.getTotalPages())
                .totalElements(hotelPage.getTotalElements())
                .data(HotelMapper.hotelResponses(hotelList))
                .build();
    }

    @Override
    public HotelResponse updateHotel(Long hotelId, UpdateHotelRequest request) throws IOException, SQLException {
        Hotel hotel = getHotelById(hotelId);
        hotel.setName(request.getHotelName());
        hotel.setAddress(request.getHotelLocation());
        hotel.setDescription(request.getHotelDescription());
        hotel.setPhoneNumber(request.getPhoneNumber());
        hotel.setCity(request.getCity());
        setHotelPhoto(hotel, request.getPhoto());
        hotel.setRegisterDay(LocalDate.now());
        hotelRepository.save(hotel);
        return HotelMapper.toHotelResponse(hotel);
    }

    @Override
    public void deleteHotel(Long hotelId){
        hotelRepository.deleteById(hotelId);
    }

    private void setHotelPhoto(Hotel hotel, MultipartFile photo) throws IOException, SQLException {
        if (photo != null && !photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            hotel.setPhoto(photoBlob);
        }
    }
}
