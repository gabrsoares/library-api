package com.library_api.infra;

import com.library_api.exception.BookNotFoundException;
import com.library_api.exception.ErrorResponseDTO;
import com.library_api.exception.ErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BookNotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> bookNotFoundHandler(BookNotFoundException ex){
        return ErrorResponseUtil.buildErrorResponse("Book not found", HttpStatus.NOT_FOUND);
    }
}
