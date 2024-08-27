package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtDTO {
    private Long id;
    private String email;
    private String token;
    private String type = "Bearer";
    private List<String> roles;

    public JwtDTO(Long id, String email, String token, List<String> roles) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.roles = roles;
    }
}
