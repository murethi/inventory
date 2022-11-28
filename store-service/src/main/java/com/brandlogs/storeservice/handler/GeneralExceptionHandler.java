package com.brandlogs.storeservice.handler;


import com.brandlogs.storeservice.exception.ApiException;
import com.brandlogs.storeservice.exception.ModelNotFoundException;
import com.brandlogs.storeservice.exception.QuantityExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<Object> handleModelNotFoundException(ModelNotFoundException e) {
        ApiException apiException = new ApiException(e.getMessage(),
                e,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuantityExceededException.class)
    public ResponseEntity<Object> handleModelNotFoundException(QuantityExceededException e) {
        ApiException apiException = new ApiException(e.getMessage(),
                e,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
}
