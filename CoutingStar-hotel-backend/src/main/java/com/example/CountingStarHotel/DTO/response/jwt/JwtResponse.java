package com.example.CountingStarHotel.DTO.response.jwt;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class JwtResponse implements Serializable {
    private Long id;
    private String email;
    private String token;
    private String type = "Bearer";
    private List<String> roles;
}
