package com.example.CoutingStarHotel.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
