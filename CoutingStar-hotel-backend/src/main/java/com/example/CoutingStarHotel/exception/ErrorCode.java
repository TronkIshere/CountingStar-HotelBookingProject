package com.example.CoutingStarHotel.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    INVALID_BOOKING_REQUEST(1001, "Booking function have error", HttpStatus.BAD_REQUEST),
    INVALID_DISCOUNT_REQUEST(1002, "Discount function have error", HttpStatus.BAD_REQUEST),
    INVALID_HOTEL_REQUEST(1003, "Hotel function have error", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST_EXCEPTION(1004, "User already exists", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXCEPTION(1005, "Role already exists", HttpStatus.BAD_REQUEST),
    USER_ALREADY_HAS_THIS_ROLE(1006, "Role already exists", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(1007, "Resource not found", HttpStatus.NOT_FOUND),


    ENTITY_IS_ALREADY_DELETED(1020,"ENTITY HAS BEEN DELETED", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1021, "Uncategorized", HttpStatus.BAD_REQUEST );


    int code;
    String message;
    HttpStatus statusCode;
}
