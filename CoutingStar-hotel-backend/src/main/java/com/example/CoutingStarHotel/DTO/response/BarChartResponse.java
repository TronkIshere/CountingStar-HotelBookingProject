package com.example.CoutingStarHotel.DTO.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class BarChartResponse implements Serializable {
    private String cityName;
    private BigDecimal revenue ;
}
