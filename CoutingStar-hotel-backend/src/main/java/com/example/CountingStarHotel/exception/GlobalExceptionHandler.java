package com.example.CountingStarHotel.exception;

import com.example.CountingStarHotel.DTO.response.common.ResponseData;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseData<?>> handlingRunTimeException(RuntimeException exception) {
        ResponseData<?> apiResponse = new ResponseData<>();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(apiResponse);
    }

    @ExceptionHandler(value = ApplicationException.class)
    ResponseEntity<ResponseData<?>> handlingAppException(ApplicationException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ResponseData<?> apiResponse = new ResponseData<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }


    @ExceptionHandler( value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseData<?>> handleValidationException(MethodArgumentNotValidException exception) {
        System.out.println("handling validation exception");
        List<String> messages = new ArrayList<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                messages.add(fieldName + ": " + defaultMessage);
            }
        }
        ResponseData<?> apiResponse = new ResponseData<>();
        apiResponse.setCode(ErrorCode.INVALID_KEY.getCode());
        apiResponse.setMessage(String.join(";", messages));
        return ResponseEntity
                .badRequest()
                .body(apiResponse);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseData<?>> handleConstraintViolation(ConstraintViolationException exception) {
        List<String> messages = exception.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ResponseData<?> response = new ResponseData<>();
        response.setCode(ErrorCode.INVALID_KEY.getCode());
        response.setMessage(String.join("; ", messages));

        return ResponseEntity.badRequest().body(response);
    }
}
