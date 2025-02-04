package com.example.CoutingStarHotel.entities;

import com.example.CoutingStarHotel.entities.common.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate registerDay;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookedRoom> bookedRooms = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Hotel hotel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> rating;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RedeemedDiscount> redeemedDiscount;

    @Enumerated(value = EnumType.STRING)

    // SELF, GOOGLE, FACEBOOK, TWITTER, LINKEDIN, GITHUB
//    private Providers provider = Providers.SELF;
//    private String providerUserId;

    public void addBooking(BookedRoom booking) {
        if (bookedRooms == null) {
            bookedRooms = new HashSet<>();
        }
        bookedRooms.add(booking);
        booking.setUser(this);
    }

    public void addHotel(Hotel hotel){
        hotel.setUser(this);
    }

    public void addComment(Rating rating) {
        rating.setUser(this);
    }

    public void addRedeemedDiscount(RedeemedDiscount redeemedDiscount) {
        redeemedDiscount.setUser(this);
    }
}