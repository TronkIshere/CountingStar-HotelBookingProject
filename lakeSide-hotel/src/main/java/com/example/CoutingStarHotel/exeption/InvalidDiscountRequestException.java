package com.example.CoutingStarHotel.exeption;

public class InvalidDiscountRequestException extends RuntimeException{
    public InvalidDiscountRequestException (String message){
        super(message);
    }
}
