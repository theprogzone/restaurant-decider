package com.govtech.restaurantdecider.exception;

import com.govtech.restaurantdecider.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = { ValidationException.class })
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(ValidationException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getMessage()));
    }
}
