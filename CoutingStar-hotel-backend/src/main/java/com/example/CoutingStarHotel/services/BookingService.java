package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.repositories.BookingRepository;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CoutingStarHotel.services.impl.BookingServiceImpl;
import com.example.CoutingStarHotel.services.impl.RedeemedDiscountServiceImpl;
import com.example.CoutingStarHotel.services.impl.RoomServiceImpl;
import com.example.CoutingStarHotel.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public
class BookingService implements BookingServiceImpl {
    private final BookingRepository bookingRepository;
    private final RoomServiceImpl roomService;
    private final UserServiceImpl userService;
    private final RedeemedDiscountServiceImpl redeemedDiscountService;
    private final RedeemedDiscountRepository redeemedDiscountRepository;

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }
    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest, Long userId, Long RedeemedDiscountId) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Room room = roomService.getRoomById(roomId).get();

        if(userId != null){
            User user = userService.getUserById(userId).get();
            user.addBooking(bookingRequest);
        }

        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable){
            room.addBooking(bookingRequest);
            long bookingDays = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
            BigDecimal roomPrice = room.getRoomPrice();
            BigDecimal daysBigDecimal = BigDecimal.valueOf(bookingDays);
            BigDecimal totalAmount = daysBigDecimal.multiply(roomPrice);

            if(RedeemedDiscountId != null){
                RedeemedDiscount redeemedDiscount = redeemedDiscountService.findRedeemedDiscountById(RedeemedDiscountId);
                if(redeemedDiscount.isUsed()) {
                    throw new InvalidBookingRequestException("Mã giảm giá đã được sử dụng, hãy chọn cái khác!");
                }
                BigDecimal discountPercent = BigDecimal.valueOf(redeemedDiscount.getDiscount().getPercentDiscount());
                BigDecimal discountAmount = totalAmount.multiply(discountPercent).divide(BigDecimal.valueOf(100));
                totalAmount = totalAmount.subtract(discountAmount);
                redeemedDiscount.setUsed(true);
                bookingRequest.addDiscount(redeemedDiscount);
                redeemedDiscountRepository.save(redeemedDiscount);
            }
            bookingRequest.setTotalAmount(totalAmount);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates;");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :"+confirmationCode));
    }

    @Override
    public List<BookedRoom> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<BookedRoom> getAllBookingsByHotelId(Long hotelId) {
        return bookingRepository.findByHotelId(hotelId);
    }

    @Override
    public BookedRoom findByBookingId(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with bookingId :"+ bookingId));
    }

    @Override
    public BookedRoom updateBooked(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestEmail, String guestPhoneNumber, String guestFullName, int totalNumOfGuest) {
        BookedRoom bookedRoom = bookingRepository.findById(bookingId).get();
        if (checkInDate != null) bookedRoom.setCheckInDate(checkInDate);
        if (checkOutDate != null) bookedRoom.setCheckOutDate(checkOutDate);
        if (guestEmail != null) bookedRoom.setGuestEmail(guestEmail);
        if (guestPhoneNumber != null) bookedRoom.setGuestPhoneNumber(guestPhoneNumber);
        if (guestFullName != null) bookedRoom.setGuestFullName(guestFullName);
        if (totalNumOfGuest > 0) bookedRoom.setTotalNumOfGuest(totalNumOfGuest);
        bookedRoom.setBookingDay(LocalDate.now());
        return bookingRepository.save(bookedRoom);
    }

    @Override
    public int getTotalNumberOfBookedRooms() {
        return bookingRepository.getTotalNumberOfBookedRooms();
    }

    @Override
    public double getPercentageOfBookedRoomsIncreasedDuringTheMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        int totalBookedRooms = bookingRepository.getTotalNumberOfBookedRooms();
        int BookedRoomsAddedThisMonth = bookingRepository.getBookedRoomsAddedDuringPeriod(firstDayOfThisMonth, firstDayOfNextMonth);

        return (BookedRoomsAddedThisMonth * 100.0) / totalBookedRooms;
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
