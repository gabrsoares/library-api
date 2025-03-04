package com.library_api.infra;

import com.library_api.exception.*;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BookNotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> bookNotFoundHandler(BookNotFoundException ex){
        return ErrorResponseUtil.buildErrorResponse("Book not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegisteredUserException.class)
    private ResponseEntity<ErrorResponseDTO> registeredUserHandler(RegisteredUserException ex){
        return ErrorResponseUtil.buildErrorResponse("There is already an user with this login", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> userNotFoundHandler(UserNotFoundException ex){
        return ErrorResponseUtil.buildErrorResponse("User not found.", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TokenValidationException.class)
    private ResponseEntity<ErrorResponseDTO> tokenValidationHandler(TokenValidationException ex){
        return ErrorResponseUtil.buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
