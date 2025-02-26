package com.example.CoutingStarHotel.data;

import com.example.CoutingStarHotel.entities.*;
import com.example.CoutingStarHotel.exception.ApplicationException;
import com.example.CoutingStarHotel.exception.ErrorCode;
import com.example.CoutingStarHotel.repositories.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    public ApplicationRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      HotelRepository hotelRepository,
                                      RoomRepository roomRepository,
                                      BookingRepository bookedRoomRepository,
                                      DiscountRepository discountRepository,
                                      RatingRepository ratingRepository,
                                      RedeemedDiscountRepository redeemedDiscountRepository) {
        return args -> {
            // insert roles data
            if (roleRepository.count() == 0) {
                List<Role> roles = List.of(
                    createRole("ROLE_USER"),
                    createRole("ROLE_HOTEL_OWNER"),
                    createRole("ROLE_ADMIN")
                );
                roleRepository.saveAll(roles);
                System.out.println("Roles inserted!");
            }

            // insert users data
            if (userRepository.count() == 0) {
                Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NOT_FOUND));
                Role hotelOwnerRole = roleRepository.findByName("ROLE_HOTEL_OWNER").orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NOT_FOUND));
                Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NOT_FOUND));

                List<User> users = List.of(
                        createUser("John", "Doe", "john@gmail.com", "123", "0123456789", Set.of(userRole)),
                        createUser("Alice", "Smith", "alice@gmail.com", "1234", "0987654321", Set.of(adminRole)),
                        createUser("Trong", "Nguyen", "tronk@gmail.com", "1235", "0987654321", Set.of(hotelOwnerRole)),
                        createUser("Hotel", "Owner", "hotel@gmail.com", "1236", "0987654321", Set.of(hotelOwnerRole)));
                userRepository.saveAll(users);
                System.out.println("Dummy Users inserted!");
            }

            // insert hotels data
            if (hotelRepository.count() == 0) {
                User owner1 = userRepository.findByEmail("tronk@gmail.com").orElse(null);
                User owner2 = userRepository.findByEmail("hotel@gmail.com").orElse(null);

                List<Hotel> hotels = List.of(
                        createHotel("Luxury Hotel", "New York", "123 5th Avenue", "A 5-star hotel", "1234567890", owner1),
                        createHotel("Budget Inn", "Los Angeles", "456 Sunset Blvd", "Affordable stay", "9876543210", owner2));
                hotelRepository.saveAll(hotels);
                System.out.println("Dummy Hotels inserted!");
            }

            //insert rooms data
            if (roomRepository.count() == 0) {
                Hotel hotel = hotelRepository.findByHotelName("Luxury Hotel");

                List<Room> rooms = List.of(
                        createRoom("Deluxe", BigDecimal.valueOf(200.0), "Luxury room with sea view", hotel),
                        createRoom("Standard", BigDecimal.valueOf(100.0), "Affordable room with city view", hotel));
                roomRepository.saveAll(rooms);
                System.out.println("Dummy Rooms inserted!");
            }

            //insert bookedRooms data
            if (bookedRoomRepository.count() == 0) {
                Room room = roomRepository.findByRoomType("Deluxe");
                User user = userRepository.findByEmail("tronk@gmail.com").get();

                List<BookedRoom> bookedRooms = List.of(
                    createBookedRoom(LocalDate.now().plusDays(1),
                            LocalDate.now().plusDays(5),
                            "0987654321",
                            "Jane Doe",
                            "jane@example.com",
                            2, 1, 3,
                            "CONF12345",
                            LocalDate.now(),
                            new BigDecimal("500.00"),
                            false,
                            room,
                            user),
                createBookedRoom(LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(5),
                        "0987654321",
                        "kinkyBoyIsHere",
                        "tronk@example.com",
                        2, 1, 3,
                        "CONF12346",
                        LocalDate.now(),
                        new BigDecimal("500.00"),
                        false,
                        room,
                        user));
                bookedRoomRepository.saveAll(bookedRooms);
                System.out.println("Dummy bookedRooms inserted!");
            }

            // insert discounts data
            if (discountRepository.count() == 0) {
                List<Discount> discounts = List.of(
                        createDiscount("Spring Sale", 20, "20% off for all bookings in spring.",
                                LocalDate.now(), LocalDate.now().plusMonths(2)),
                        createDiscount("autumn Sale", 20, "20% off for all bookings in spring.",
                                LocalDate.now(), LocalDate.now().plusMonths(2)));
                discountRepository.saveAll(discounts);
                System.out.println("Dummy discounts inserted!");
            }

            // insert redeemedDiscounts data
            if (redeemedDiscountRepository.count() == 0) {
                Discount discount1 = discountRepository.findByName("Spring Sale");
                User user1 = userRepository.findByEmail("tronk@gmail.com").get();
                BookedRoom bookedRoom1 = bookedRoomRepository.findByBookingConfirmationCode("CONF12346").get();

                List<RedeemedDiscount> redeemedDiscounts = List.of(
                        createRedeemedDiscount(true, discount1, user1, bookedRoom1)
                );
                redeemedDiscountRepository.saveAll(redeemedDiscounts);
                System.out.println("Dummy redeemedDiscounts inserted!");
            }

            // insert ratings data
            if (ratingRepository.count() == 0) {
                User user1 = userRepository.findByEmail("tronk@gmail.com").get();
                BookedRoom bookedRoom1 = bookedRoomRepository.findByBookingConfirmationCode("CONF12346").get();

                List<Rating> ratings = List.of(
                        createRating(5, "Great experience, highly recommend!", LocalDate.now(), user1, bookedRoom1),
                        createRating(4, "highly recommend!", LocalDate.now(), user1, bookedRoom1)
                );
                ratingRepository.saveAll(ratings);
                System.out.println("Dummy ratings inserted!");
            }
        };
    }

    private Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    private User createUser(String firstName, String lastName, String email, String password, String phone, Set<Role> roles) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phone);
        user.setRegisterDay(LocalDate.now());
        user.setRoles(roles);
        System.out.println("Check user roles: " + user.getRoles().size());
        return user;
    }

    private Hotel createHotel(String name, String city, String address, String description, String phone, User owner) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setCity(city);
        hotel.setAddress(address);
        hotel.setDescription(description);
        hotel.setPhoneNumber(phone);
        hotel.setUser(owner);
        return hotel;
    }

    private Room createRoom(String type, BigDecimal price, String description, Hotel hotel) {
        Room room = new Room();
        room.setRoomType(type);
        room.setRoomPrice(price);
        room.setRoomDescription(description);
        room.setBooked(false);
        room.setHotel(hotel);
        return room;
    }

    private BookedRoom createBookedRoom(LocalDate checkInDate,
                                        LocalDate checkOutDate,
                                        String guestPhoneNumber,
                                        String guestFullName,
                                        String guestEmail,
                                        int numOfAdults,
                                        int numOfChildren,
                                        int totalNumOfGuest,
                                        String bookingConfirmationCode,
                                        LocalDate bookingDay,
                                        BigDecimal totalAmount,
                                        Boolean isCancelled,
                                        Room room,
                                        User user) {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckInDate(checkInDate);
        bookedRoom.setCheckOutDate(checkOutDate);
        bookedRoom.setGuestPhoneNumber(guestPhoneNumber);
        bookedRoom.setGuestFullName(guestFullName);
        bookedRoom.setGuestEmail(guestEmail);
        bookedRoom.setNumOfAdults(numOfAdults);
        bookedRoom.setNumOfChildren(numOfChildren);
        bookedRoom.setTotalNumOfGuest(totalNumOfGuest);
        bookedRoom.setBookingConfirmationCode(bookingConfirmationCode);
        bookedRoom.setBookingDay(bookingDay);
        bookedRoom.setTotalAmount(totalAmount);
        bookedRoom.setIsCancelled(isCancelled);
        bookedRoom.setRoom(room);
        bookedRoom.setUser(user);
        return bookedRoom;
    }

    private Discount createDiscount(String discountName, int percentDiscount, String discountDescription,
                                    LocalDate createDate, LocalDate expirationDate) {
        Discount discount = new Discount();
        discount.setDiscountName(discountName);
        discount.setPercentDiscount(percentDiscount);
        discount.setDiscountDescription(discountDescription);
        discount.setCreateDate(createDate);
        discount.setExpirationDate(expirationDate);
        return discount;
    }

    private RedeemedDiscount createRedeemedDiscount(boolean isUsed, Discount discount, User user, BookedRoom bookedRoom) {
        RedeemedDiscount redeemedDiscount = new RedeemedDiscount();
        redeemedDiscount.setUsed(isUsed);
        redeemedDiscount.setDiscount(discount);
        redeemedDiscount.setUser(user);
        redeemedDiscount.setBookedRoom(bookedRoom);
        return redeemedDiscount;
    }

    private Rating createRating(int star, String comment, LocalDate rateDay, User user, BookedRoom bookedRoom) {
        Rating rating = new Rating();
        rating.setStar(star);
        rating.setComment(comment);
        rating.setRateDay(rateDay);
        rating.setUser(user);
        rating.setBookedRoom(bookedRoom);
        return rating;
    }
}