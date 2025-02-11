package com.library_api.controller;

import com.library_api.repository.BookRepository;
import com.library_api.model.Book.BookResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    BookRepository repository;
    @GetMapping
    public ResponseEntity getAllBooks(){
        List<BookResponseDTO> allBooks = this.repository.findAll().stream().map(BookResponseDTO::new).toList();

        return ResponseEntity.ok(allBooks);
    }
}
