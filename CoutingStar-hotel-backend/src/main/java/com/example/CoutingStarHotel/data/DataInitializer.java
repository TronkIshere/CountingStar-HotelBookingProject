package com.example.CoutingStarHotel.data;

import com.example.CoutingStarHotel.entities.*;
import com.example.CoutingStarHotel.exception.ApplicationException;
import com.example.CoutingStarHotel.exception.ErrorCode;
import com.example.CoutingStarHotel.repositories.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
                        createUser("Alice", "Smith", "alice@gmail.com", "1234", "0987654321", Set.of(adminRole)),
                        createUser("Trong", "Nguyen", "tronk@gmail.com", "1235", "0987654321", Set.of(hotelOwnerRole)),
                        createUser("Hotel", "Owner", "hotel@gmail.com", "1236", "0987654321", Set.of(hotelOwnerRole)),
                        createUser("David", "Harris", "david@gmail.com", "1243", "0978901234", Set.of(hotelOwnerRole)),
                        createUser("Emily", "Clark", "emily@gmail.com", "1244", "0989012345", Set.of(hotelOwnerRole)),
                        createUser("John", "Doe", "john@gmail.com", "123", "0123456789", Set.of(userRole)),
                        createUser("Michael", "Johnson", "michael@gmail.com", "1237", "0912345678", Set.of(userRole)),
                        createUser("Emma", "Brown", "emma@gmail.com", "1238", "0923456789", Set.of(userRole)),
                        createUser("Oliver", "Williams", "oliver@gmail.com", "1239", "0934567890", Set.of(userRole)),
                        createUser("Sophia", "Jones", "sophia@gmail.com", "1240", "0945678901", Set.of(userRole)),
                        createUser("Liam", "Miller", "liam@gmail.com", "1241", "0956789012", Set.of(userRole)),
                        createUser("Isabella", "Davis", "isabella@gmail.com", "1242", "0967890123", Set.of(userRole)));
                userRepository.saveAll(users);
                System.out.println("Dummy Users inserted!");
            }

            // insert hotels data
            if (hotelRepository.count() == 0) {
                String imagePath1 = "static/dummyImg/hotel1.jpg";
                String imagePath2 = "static/dummyImg/hotel2.jpg";
                String imagePath3 = "static/dummyImg/hotel3.jpg";
                String imagePath4 = "static/dummyImg/hotel4.jpg";

                User owner1 = userRepository.findByEmail("tronk@gmail.com").orElse(null);
                User owner2 = userRepository.findByEmail("hotel@gmail.com").orElse(null);
                User owner3 = userRepository.findByEmail("david@gmail.com").orElse(null);
                User owner4 = userRepository.findByEmail("emily@gmail.com").orElse(null);

                List<Hotel> hotels = List.of(
                        createHotel("Luxury Hotel", "New York", "123 5th Avenue", "A 5-star hotel", "1234567890", imagePath1, owner1),
                        createHotel("Budget Inn", "Los Angeles", "456 Sunset Blvd", "Affordable stay", "9876543210", imagePath2, owner2),
                        createHotel("Seaside Resort", "Miami", "789 Ocean Drive", "Beachfront luxury", "1122334455", imagePath3, owner3),
                        createHotel("Mountain Lodge", "Denver", "321 Rocky Road", "Scenic mountain retreat", "5566778899", imagePath4, owner4));
                hotelRepository.saveAll(hotels);
                System.out.println("Dummy Hotels inserted!");
            }

            //insert rooms data
            if (roomRepository.count() == 0) {
                String imagePath1 = "static/dummyImg/room1.jpg";
                String imagePath2 = "static/dummyImg/room2.jpg";
                String imagePath3 = "static/dummyImg/room3.jpg";
                String imagePath4 = "static/dummyImg/room4.jpg";
                String imagePath5 = "static/dummyImg/room5.jpg";


                List<Hotel> hotels = hotelRepository.findAll();
                List<Room> rooms = new ArrayList<>();

                for (Hotel hotel : hotels) {
                    rooms.addAll(List.of(
                            createRoom("Deluxe Suite", BigDecimal.valueOf(250.0), "Spacious suite with modern amenities", imagePath1, hotel),
                            createRoom("Standard Room", BigDecimal.valueOf(120.0), "Comfortable standard room", imagePath2, hotel),
                            createRoom("Executive Suite", BigDecimal.valueOf(300.0), "Luxurious suite with city view", imagePath3, hotel),
                            createRoom("Family Room", BigDecimal.valueOf(180.0), "Ideal for families, with extra beds", imagePath4, hotel),
                            createRoom("Penthouse", BigDecimal.valueOf(500.0), "Top floor luxury suite", imagePath5, hotel)
                    ));
                }

                roomRepository.saveAll(rooms);
                System.out.println("Dummy Rooms inserted!");
            }

            //insert bookedRooms data
            if (bookedRoomRepository.count() == 0) {
                List<Room> rooms = roomRepository.findAll();
                List<User> users = userRepository.findAll();
                List<BookedRoom> bookedRooms = new ArrayList<>();
                Random random = new Random();

                for (int i = 1; i <= 30; i++) {
                    Room room = rooms.get(random.nextInt(rooms.size()));
                    User user = users.get(random.nextInt(users.size()));

                    LocalDate checkIn = LocalDate.now().plusDays(random.nextInt(30) + 1);
                    LocalDate checkOut = checkIn.plusDays(random.nextInt(7) + 1);
                    int numAdults = random.nextInt(3) + 1;
                    int numChildren = random.nextInt(2);
                    int totalGuests = numAdults + numChildren;
                    BigDecimal totalAmount = new BigDecimal((100 + random.nextInt(401)) * totalGuests);

                    bookedRooms.add(createBookedRoom(
                            checkIn,
                            checkOut,
                            "09" + (100000000 + random.nextInt(900000000)),
                            "Guest " + i,
                            "guest" + i + "@example.com",
                            numAdults,
                            numChildren,
                            totalGuests,
                            "CONF" + (12345 + i),
                            LocalDate.now(),
                            totalAmount,
                            random.nextBoolean(),
                            room,
                            user
                    ));
                }

                bookedRoomRepository.saveAll(bookedRooms);
                System.out.println("Dummy bookedRooms inserted!");
            }

            // insert discount data
            if (discountRepository.count() == 0) {
                List<Discount> discounts = List.of(
                        createDiscount("Spring Sale", 20, "20% off for all bookings in spring.",
                                LocalDate.now(), LocalDate.now().plusMonths(2)),
                        createDiscount("Autumn Special", 15, "15% off for all bookings in autumn.",
                                LocalDate.now(), LocalDate.now().plusMonths(3)),
                        createDiscount("Winter Wonder", 25, "25% off for stays during the winter season.",
                                LocalDate.now(), LocalDate.now().plusMonths(4)),
                        createDiscount("Weekend Getaway", 10, "10% discount for weekend bookings.",
                                LocalDate.now(), LocalDate.now().plusMonths(6)),
                        createDiscount("Early Bird", 30, "30% off for bookings made 2 months in advance.",
                                LocalDate.now(), LocalDate.now().plusMonths(12)),
                        createDiscount("Last Minute Deal", 40, "40% off for same-day bookings.",
                                LocalDate.now(), LocalDate.now().plusWeeks(2)),
                        createDiscount("New Year Special", 35, "Exclusive 35% off for New Year’s Eve stays.",
                                LocalDate.now(), LocalDate.now().plusMonths(1)),
                        createDiscount("Summer Madness", 18, "Enjoy 18% off during the summer holidays.",
                                LocalDate.now(), LocalDate.now().plusMonths(5)),
                        createDiscount("Black Friday Deal", 50, "Huge 50% discount on all bookings on Black Friday.",
                                LocalDate.now(), LocalDate.now().plusDays(7)),
                        createDiscount("Christmas Discount", 22, "Merry Christmas! Get 22% off your holiday stays.",
                                LocalDate.now(), LocalDate.now().plusMonths(2)),
                        createDiscount("Student Discount", 12, "12% discount for students with valid ID.",
                                LocalDate.now(), LocalDate.now().plusMonths(6)),
                        createDiscount("VIP Exclusive", 20, "Special 20% discount for VIP members only.",
                                LocalDate.now(), LocalDate.now().plusMonths(8)),
                        createDiscount("Family Package", 15, "15% off for families booking more than one room.",
                                LocalDate.now(), LocalDate.now().plusMonths(3)),
                        createDiscount("Loyalty Reward", 10, "Loyal customers enjoy 10% off every booking.",
                                LocalDate.now(), LocalDate.now().plusMonths(12)),
                        createDiscount("Honeymoon Special", 25, "Couples get 25% off for their honeymoon stay.",
                                LocalDate.now(), LocalDate.now().plusMonths(4)),
                        createDiscount("Flash Sale", 35, "Book within the next 24 hours and get 35% off.",
                                LocalDate.now(), LocalDate.now().plusDays(1)),
                        createDiscount("Military Appreciation", 20, "20% discount for military personnel and veterans.",
                                LocalDate.now(), LocalDate.now().plusMonths(10)),
                        createDiscount("Senior Citizen Discount", 15, "Seniors (60+) get 15% off.",
                                LocalDate.now(), LocalDate.now().plusMonths(12)),
                        createDiscount("Business Traveler", 12, "12% discount for corporate bookings.",
                                LocalDate.now(), LocalDate.now().plusMonths(6)),
                        createDiscount("Cultural Festival Offer", 18, "18% discount for bookings during city festivals.",
                                LocalDate.now(), LocalDate.now().plusMonths(5))
                );
                discountRepository.saveAll(discounts);
                System.out.println("Dummy discounts inserted!");
            }

            // insert redeemedDiscounts data
            if (redeemedDiscountRepository.count() == 0) {
                Discount springSale = discountRepository.findByName("Spring Sale");
                Discount blackFriday = discountRepository.findByName("Black Friday Deal");
                Discount earlyBird = discountRepository.findByName("Early Bird");
                Discount honeymoon = discountRepository.findByName("Honeymoon Special");
                Discount vipExclusive = discountRepository.findByName("VIP Exclusive");
                Discount christmas = discountRepository.findByName("Christmas Discount");

                User user1 = userRepository.findByEmail("tronk@gmail.com").get();
                User user2 = userRepository.findByEmail("emma@gmail.com").get();
                User user3 = userRepository.findByEmail("oliver@gmail.com").get();
                User user4 = userRepository.findByEmail("emily@gmail.com").get();
                User user5 = userRepository.findByEmail("hotel@gmail.com").get();

                BookedRoom bookedRoom1 = bookedRoomRepository.findByBookingConfirmationCode("CONF12346").get();
                BookedRoom bookedRoom2 = bookedRoomRepository.findByBookingConfirmationCode("CONF12347").get();
                BookedRoom bookedRoom3 = bookedRoomRepository.findByBookingConfirmationCode("CONF12348").get();
                BookedRoom bookedRoom4 = bookedRoomRepository.findByBookingConfirmationCode("CONF12349").get();
                BookedRoom bookedRoom5 = bookedRoomRepository.findByBookingConfirmationCode("CONF12350").get();
                BookedRoom bookedRoom6 = bookedRoomRepository.findByBookingConfirmationCode("CONF12351").get();
                BookedRoom bookedRoom7 = bookedRoomRepository.findByBookingConfirmationCode("CONF12352").get();
                BookedRoom bookedRoom8 = bookedRoomRepository.findByBookingConfirmationCode("CONF12353").get();
                BookedRoom bookedRoom9 = bookedRoomRepository.findByBookingConfirmationCode("CONF12354").get();
                BookedRoom bookedRoom10 = bookedRoomRepository.findByBookingConfirmationCode("CONF12355").get();

                List<RedeemedDiscount> redeemedDiscounts = List.of(
                        createRedeemedDiscount(true, springSale, user1, bookedRoom1),
                        createRedeemedDiscount(true, blackFriday, user2, bookedRoom2),
                        createRedeemedDiscount(true, earlyBird, user3, bookedRoom3),
                        createRedeemedDiscount(true, honeymoon, user4, bookedRoom4),
                        createRedeemedDiscount(true, vipExclusive, user5, bookedRoom5),
                        createRedeemedDiscount(true, christmas, user1, bookedRoom6),
                        createRedeemedDiscount(true, earlyBird, user2, bookedRoom7),
                        createRedeemedDiscount(true, blackFriday, user3, bookedRoom8),
                        createRedeemedDiscount(true, honeymoon, user4, bookedRoom9),
                        createRedeemedDiscount(true, vipExclusive, user5, bookedRoom10)
                );

                redeemedDiscountRepository.saveAll(redeemedDiscounts);
                System.out.println("Dummy redeemedDiscounts inserted!");
            }

            // insert ratings data
            if (ratingRepository.count() == 0) {
                User user1 = userRepository.findByEmail("tronk@gmail.com").get();
                User user2 = userRepository.findByEmail("liam@gmail.com").get();
                User user3 = userRepository.findByEmail("david@gmail.com").get();
                User user4 = userRepository.findByEmail("emily@gmail.com").get();
                User user5 = userRepository.findByEmail("hotel@gmail.com").get();

                BookedRoom bookedRoom1 = bookedRoomRepository.findByBookingConfirmationCode("CONF12346").get();
                BookedRoom bookedRoom2 = bookedRoomRepository.findByBookingConfirmationCode("CONF12347").get();
                BookedRoom bookedRoom3 = bookedRoomRepository.findByBookingConfirmationCode("CONF12348").get();
                BookedRoom bookedRoom4 = bookedRoomRepository.findByBookingConfirmationCode("CONF12349").get();
                BookedRoom bookedRoom5 = bookedRoomRepository.findByBookingConfirmationCode("CONF12350").get();

                List<Rating> ratings = List.of(
                        createRating(5, "Amazing stay, everything was perfect!", LocalDate.now(), user1, bookedRoom1),
                        createRating(4, "Very comfortable and clean.", LocalDate.now(), user2, bookedRoom2),
                        createRating(3, "Good, but could be better.", LocalDate.now(), user3, bookedRoom3),
                        createRating(5, "Outstanding service, will come back!", LocalDate.now(), user4, bookedRoom4),
                        createRating(2, "Not satisfied, room was not clean.", LocalDate.now(), user5, bookedRoom5),
                        createRating(4, "Highly recommend this place!", LocalDate.now(), user1, bookedRoom2),
                        createRating(3, "Nice view but a bit noisy at night.", LocalDate.now(), user2, bookedRoom3),
                        createRating(5, "Best hotel experience so far!", LocalDate.now(), user3, bookedRoom4),
                        createRating(1, "Terrible service, will not return.", LocalDate.now(), user4, bookedRoom5),
                        createRating(5, "Fantastic place, great amenities!", LocalDate.now(), user5, bookedRoom1),
                        createRating(4, "Comfortable and spacious rooms.", LocalDate.now(), user1, bookedRoom3),
                        createRating(3, "Average experience, nothing special.", LocalDate.now(), user2, bookedRoom4),
                        createRating(5, "Loved the breakfast and view!", LocalDate.now(), user3, bookedRoom5),
                        createRating(2, "Overpriced for the quality.", LocalDate.now(), user4, bookedRoom1),
                        createRating(5, "Exceptional service from staff!", LocalDate.now(), user5, bookedRoom2),
                        createRating(4, "Would definitely stay again!", LocalDate.now(), user1, bookedRoom4),
                        createRating(3, "Decent for the price, but noisy.", LocalDate.now(), user2, bookedRoom5),
                        createRating(5, "One of the best hotels I've been to!", LocalDate.now(), user3, bookedRoom1),
                        createRating(2, "AC was broken, uncomfortable stay.", LocalDate.now(), user4, bookedRoom2),
                        createRating(4, "Nice ambiance, friendly staff.", LocalDate.now(), user5, bookedRoom3)
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

    private Hotel createHotel(String name, String city, String address, String description, String phone, String imagePath, User owner) throws SQLException, IOException {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setCity(city);
        hotel.setAddress(address);
        hotel.setDescription(description);
        hotel.setPhoneNumber(phone);
        hotel.setPhoto(convertImageToBlob(imagePath));
        hotel.setUser(owner);
        return hotel;
    }

    private Room createRoom(String type, BigDecimal price, String description, String imagePath, Hotel hotel) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(type);
        room.setRoomPrice(price);
        room.setRoomDescription(description);
        room.setBooked(false);
        room.setPhoto(convertImageToBlob(imagePath));
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

    private static Blob convertImageToBlob(String imagePath) throws IOException, SQLException {
        ClassPathResource resource = new ClassPathResource(imagePath);
        InputStream inputStream = resource.getInputStream();

        byte[] imageBytes = inputStream.readAllBytes();
        inputStream.close();

        return new SerialBlob(imageBytes);
    }
}