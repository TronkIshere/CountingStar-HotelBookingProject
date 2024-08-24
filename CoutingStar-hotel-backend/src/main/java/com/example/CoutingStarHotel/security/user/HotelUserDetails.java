package com.example.CoutingStarHotel.security.user;

import com.example.CoutingStarHotel.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    private Collection<GrantedAuthority> authorities;
    private Long hotelId;
    public static HotelUserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        Long hotelId = user.getHotel() != null ? user.getHotel().getId() : null;
        System.out.println("HotelId: " + hotelId);
        return new HotelUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                authorities,
                hotelId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
}
