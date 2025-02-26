package com.library_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponseUtil {

    public static ResponseEntity<ErrorResponseDTO> buildErrorResponse(String message, HttpStatus status){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(message, status);
        return ResponseEntity.status(status).body(errorResponseDTO);
    }
}
