package com.example.CountingStarHotel.entities;

import com.example.CountingStarHotel.entities.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractEntity<Long> implements UserDetails {
    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    LocalDate registerDay;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    Collection<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<BookedRoom> bookedRooms = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Hotel hotel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Rating> rating;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<RedeemedDiscount> redeemedDiscount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    @Enumerated(value = EnumType.STRING)
    public void addBooking(BookedRoom booking) {
        if (bookedRooms == null) {
            bookedRooms = new HashSet<>();
        }
        bookedRooms.add(booking);
        booking.setUser(this);
    }
}