package com.example.CoutingStarHotel.exception;

public class InvalidDiscountRequestException extends RuntimeException{
    public InvalidDiscountRequestException (String message){
        super(message);
    }
}
