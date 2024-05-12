package com.example.lakesidehotel.exeption;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mesage) {
        super(mesage);
    }
}
