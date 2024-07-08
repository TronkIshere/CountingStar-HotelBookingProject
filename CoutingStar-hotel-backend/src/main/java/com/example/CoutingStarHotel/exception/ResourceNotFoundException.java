package com.example.CoutingStarHotel.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mesage) {
        super(mesage);
    }
}
