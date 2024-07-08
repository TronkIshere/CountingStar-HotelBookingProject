package com.example.CoutingStarHotel.exception;

public class InvalidHotelRequestException extends RuntimeException{
    public InvalidHotelRequestException (String message){
        super(message);
    }
}
