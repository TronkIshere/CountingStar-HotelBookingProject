package com.example.CoutingStarHotel.DTO.request.room;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class UpdateRoomRequest implements Serializable {
    @NotBlank
    private String roomType;

    @NotBlank
    private String roomDescription;

    @NotBlank
    private BigDecimal roomPrice;

    @NotBlank
    private MultipartFile photo;
}
