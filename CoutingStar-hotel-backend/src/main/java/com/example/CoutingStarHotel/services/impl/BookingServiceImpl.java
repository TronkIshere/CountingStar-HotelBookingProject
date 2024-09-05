package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.repositories.BookingRepository;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CoutingStarHotel.services.BookingService;
import com.example.CoutingStarHotel.services.RedeemedDiscountService;
import com.example.CoutingStarHotel.services.RoomService;
import com.example.CoutingStarHotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final RedeemedDiscountService redeemedDiscountService;
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
    public String saveBooking(Long roomId, BookedRoom bookingRequest, Long userId, Long redeemedDiscountId) {
        validateBookingDates(bookingRequest);
        if (userId != null) { handleUserBooking(userId, bookingRequest);}
        Room room = getRoom(roomId);
        validateRoomAvailability(bookingRequest, room);
        BigDecimal totalAmount = calculateTotalAmount(bookingRequest, room.getRoomPrice());
        if (redeemedDiscountId != null) {
            totalAmount = applyDiscountIfValid(redeemedDiscountId, totalAmount, bookingRequest);
        }
        bookingRequest.setTotalAmount(totalAmount);
        bookingRequest.setBookingDay(LocalDate.now());
        bookingRepository.save(bookingRequest);
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

    private void validateBookingDates(BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
    }

    private Room getRoom(Long roomId) {
        return roomService.getRoomById(roomId).orElseThrow(() ->
                new InvalidBookingRequestException("Room not found"));
    }

    private void validateRoomAvailability(BookedRoom bookingRequest, Room room) {
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, room.getBookings());
        if (!roomIsAvailable) {
            throw new InvalidBookingRequestException("Phòng hiện đã đặt trong khoảng thời gian này, xin hãy đặt trong khoảng thời gian khác!");
        }
        room.addBooking(bookingRequest);
    }

    private BigDecimal calculateTotalAmount(BookedRoom bookingRequest, BigDecimal roomPrice) {
        return getBookedRoomTotalAmount(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), roomPrice);
    }

    private BigDecimal applyDiscountIfValid(Long redeemedDiscountId, BigDecimal totalAmount, BookedRoom bookingRequest) {
        RedeemedDiscount redeemedDiscount = redeemedDiscountService.findRedeemedDiscountById(redeemedDiscountId);

        if (redeemedDiscount.isUsed()) {
            throw new InvalidBookingRequestException("Mã giảm giá đã được sử dụng, hãy chọn cái khác!");
        }

        return handleDiscountAndReturnTotalAmount(redeemedDiscount, totalAmount, bookingRequest);
    }

    private BigDecimal getBookedRoomTotalAmount(LocalDate checkInDate, LocalDate checkOutDate, BigDecimal roomPrice){
        long bookingDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        BigDecimal daysBigDecimal = BigDecimal.valueOf(bookingDays);
        BigDecimal totalAmount = daysBigDecimal.multiply(roomPrice);
        return totalAmount;
    }

    private BigDecimal handleDiscountAndReturnTotalAmount(RedeemedDiscount redeemedDiscount, BigDecimal totalAmount, BookedRoom bookingRequest){
        BigDecimal discountPercent = BigDecimal.valueOf(redeemedDiscount.getDiscount().getPercentDiscount());
        BigDecimal discountAmount = totalAmount.multiply(discountPercent).divide(BigDecimal.valueOf(100));
        totalAmount = totalAmount.subtract(discountAmount);
        redeemedDiscount.setUsed(true);
        bookingRequest.addDiscount(redeemedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
        return totalAmount;
    }

    private void handleUserBooking(Long userId, BookedRoom bookingRequest){
        User user = userService.getUserById(userId).get();
        user.addBooking(bookingRequest);
    }

    private boolean getAllRoomAndCheckRoomIsAvailable(Room room, BookedRoom bookingRequest) {
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        return roomIsAvailable;
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
