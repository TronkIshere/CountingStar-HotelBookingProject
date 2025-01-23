package com.example.CoutingStarHotel.entities;

import com.example.CoutingStarHotel.entities.common.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Hotel extends BaseEntity {
    private String hotelName;
    private String city;
    private String hotelLocation;
    @Column(columnDefinition = "LONGTEXT")
    private String hotelDescription;
    private String phoneNumber;
    private LocalDate registerDay;

    @Lob
    @JsonIgnore
    private Blob photo;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addRoom(Room room) {
        rooms.add(room);
        room.setHotel(this);
    }
}
