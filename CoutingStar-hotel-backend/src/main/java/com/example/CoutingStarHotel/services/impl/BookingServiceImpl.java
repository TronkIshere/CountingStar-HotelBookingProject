package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.request.bookedroom.SaveBookingRequest;
import com.example.CoutingStarHotel.DTO.request.bookedroom.UpdateBookedRoom;
import com.example.CoutingStarHotel.DTO.response.bookedroom.BookingResponse;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.mapper.BookedRoomMapper;
import com.example.CoutingStarHotel.repositories.BookingRepository;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CoutingStarHotel.services.BookingService;
import com.example.CoutingStarHotel.services.RedeemedDiscountService;
import com.example.CoutingStarHotel.services.RoomService;
import com.example.CoutingStarHotel.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;
    RoomService roomService;
    UserService userService;
    RedeemedDiscountService redeemedDiscountService;
    RedeemedDiscountRepository redeemedDiscountRepository;

    @Override
    public PageResponse<BookingResponse> getAllBookings(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.findAll(pageable);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        BookedRoom bookedRoom = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));

        bookedRoom.setIsCancelled(true);
        bookingRepository.save(bookedRoom);
    }

    @Override
    public BookingResponse saveBooking(Long roomId, SaveBookingRequest request, Long userId, Long redeemedDiscountId) {
        try {
            BookedRoom bookedRoom = createBookedRoom(request);
            validateBookingDates(bookedRoom);
            handleRoomBooking(roomId, bookedRoom);
            handleUserBooking(userId, bookedRoom);
            applyDiscountIfValid(redeemedDiscountId, bookedRoom, roomId);
            bookingRepository.save(bookedRoom);
            return BookedRoomMapper.toBookingResponse(bookedRoom);
        } catch (InvalidBookingRequestException e) {
            throw new InvalidBookingRequestException("Error!" + e.getMessage());
        }
    }

    private void applyDiscountIfValid(Long redeemedDiscountId, BookedRoom bookedRoom, Long roomId) {
        if (redeemedDiscountId == null) return;
        Room room = roomService.getRoomById(roomId);
        BigDecimal totalAmount = calculateTotalAmount(bookedRoom, room.getRoomPrice());
        totalAmount = applyDiscountIfValid(redeemedDiscountId, totalAmount, bookedRoom);
        bookedRoom.setTotalAmount(totalAmount);
    }

    private BookedRoom createBookedRoom(SaveBookingRequest request) {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckInDate(request.getCheckInDate());
        bookedRoom.setCheckOutDate(request.getCheckOutDate());
        bookedRoom.setGuestFullName(request.getGuestFullName());
        bookedRoom.setGuestEmail(request.getGuestEmail());
        bookedRoom.setNumOfAdults(request.getNumOfAdults());
        bookedRoom.setNumOfChildren(request.getNumOfChildren());
        bookedRoom.setTotalNumOfGuest(request.getTotalNumOfGuest());
        bookedRoom.setGuestPhoneNumber(request.getGuestPhoneNumber());
        validateBookingDates(bookedRoom);
        bookedRoom.setBookingDay(LocalDate.now());
        return bookedRoom;
    }

    private void validateBookingDates(BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
    }

    private void handleRoomBooking(Long roomId, BookedRoom bookedRoom) {
        Room room = roomService.getRoomById(roomId);
        room.addBooking(bookedRoom);
        bookedRoom.setRoom(room);
    }

    private void handleUserBooking(Long userId, BookedRoom bookingRequest) {
        if (userId == null) return;
        User user = userService.getUserById(userId);
        user.addBooking(bookingRequest);
    }

    private BigDecimal calculateTotalAmount(BookedRoom bookingRequest, BigDecimal roomPrice) {
        return getBookedRoomTotalAmount(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), roomPrice);
    }

    private BigDecimal applyDiscountIfValid(Long redeemedDiscountId, BigDecimal totalAmount, BookedRoom bookingRequest) {
        RedeemedDiscount redeemedDiscount = redeemedDiscountService.findRedeemedDiscountById(redeemedDiscountId);
        if (redeemedDiscount.isUsed()) {
            throw new InvalidBookingRequestException("This discount have been redeemed!");
        }

        return handleDiscountAndReturnTotalAmount(redeemedDiscount, totalAmount, bookingRequest);
    }

    private BigDecimal getBookedRoomTotalAmount(LocalDate checkInDate, LocalDate checkOutDate, BigDecimal roomPrice) {
        long bookingDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        BigDecimal daysBigDecimal = BigDecimal.valueOf(bookingDays);
        return daysBigDecimal.multiply(roomPrice);
    }

    private BigDecimal handleDiscountAndReturnTotalAmount(RedeemedDiscount redeemedDiscount, BigDecimal totalAmount, BookedRoom bookingRequest) {
        BigDecimal discountPercent = BigDecimal.valueOf(redeemedDiscount.getDiscount().getPercentDiscount());
        BigDecimal discountAmount = totalAmount.multiply(discountPercent)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        totalAmount = totalAmount.subtract(discountAmount);
        redeemedDiscount.setUsed(true);
        bookingRequest.addDiscount(redeemedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
        return totalAmount;
    }

    @Override
    public BookingResponse findByBookingConfirmationCode(String confirmationCode) {
        BookedRoom bookedRoom = bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :" + confirmationCode));

        return BookedRoomMapper.toBookingResponse(bookedRoom);
    }

    @Override
    public PageResponse<BookingResponse> getBookingsByUserId(Integer pageNo, Integer pageSize, Long userId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.findByUserId(pageable, userId);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize((pageable.getPageSize()))
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public PageResponse<BookingResponse> getAllBookingsByHotelId(Integer pageNo, Integer pageSize, Long hotelId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.findByHotelId(pageable, hotelId);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public BookingResponse findBookingResponseById(Long id) {
        BookedRoom bookedRoom = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with bookingId :" + id));
        return BookedRoomMapper.toBookingResponse(bookedRoom);
    }

    @Override
    public BookedRoom findBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with bookingId :" + id));
    }

    @Override
    public BookingResponse updateBooked(Long id, UpdateBookedRoom request) {
        BookedRoom bookedRoom = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with bookingId :" + id));
        bookedRoom.setCheckInDate(request.getCheckInDate());
        bookedRoom.setCheckOutDate(request.getCheckOutDate());
        bookedRoom.setGuestEmail(request.getGuestEmail());
        bookedRoom.setGuestPhoneNumber(request.getGuestPhoneNumber());
        bookedRoom.setGuestFullName(request.getGuestFullName());
        bookedRoom.setTotalNumOfGuest(request.getTotalNumOfGuest());
        bookedRoom.setBookingDay(LocalDate.now());
        bookingRepository.save(bookedRoom);
        return BookedRoomMapper.toBookingResponse(bookedRoom);
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

    @Override
    public PageResponse<BookingResponse> getAllBookingByKeywordAndHotelId(Integer pageNo, Integer pageSize, Long hotelId, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.getAllBookingByKeywordAndHotelId(pageable, hotelId, keyword);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public BookedRoom findRoomUserHasBookedAndNotComment(Long hotelId, Long userId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<BookedRoom> bookedRooms = bookingRepository.findRoomsUserHasBookedAndNotComment(hotelId, userId, pageable);

        if (bookedRooms.isEmpty()) {
            throw new RuntimeException("No booked room found for user without a rating in the specified hotel");
        }

        return bookedRooms.get(0);
    }
}
