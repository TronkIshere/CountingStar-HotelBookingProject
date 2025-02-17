package com.example.CoutingStarHotel.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class AddRoomRequest implements Serializable {
    @NotBlank
    private MultipartFile photo;

    @NotBlank
    private String roomType;

    @NotBlank
    private BigDecimal roomPrice;

    @NotBlank
    private String roomDescription;
}
