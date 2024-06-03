package com.example.CoutingStarHotel.exeption;

public class InvalidHotelRequestException extends RuntimeException{
    public InvalidHotelRequestException (String message){
        super(message);
    }
}
