package com.library_api.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(String error, HttpStatus status) {
}
