package com.example.CoutingStarHotel.entities;

import com.example.CoutingStarHotel.entities.common.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hotel extends AbstractEntity<Long> {
    String name;
    String city;
    String address;
    @Column(columnDefinition = "LONGTEXT")
    String description;
    String phoneNumber;
    LocalDate registerDay;

    @Lob
    @JsonIgnore
    Blob photo;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Room> rooms;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public void addRoom(Room room) {
        rooms.add(room);
        room.setHotel(this);
    }
}
