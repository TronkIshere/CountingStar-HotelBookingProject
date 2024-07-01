package com.example.CoutingStarHotel.exeption;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mesage) {
        super(mesage);
    }
}
