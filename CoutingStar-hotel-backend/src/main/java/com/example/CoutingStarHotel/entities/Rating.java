package com.example.CoutingStarHotel.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Rating {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int star;
    private String comment;
    private LocalDate rateDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookedRoom_id")
    private BookedRoom bookedRoom;
}
