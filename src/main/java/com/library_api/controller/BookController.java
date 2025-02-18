package com.library_api.controller;

import com.library_api.exception.ErrorResponse;
import com.library_api.model.book.Book;
import com.library_api.model.book.BookPostDTO;
import com.library_api.repository.BookRepository;
import com.library_api.model.book.BookResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("books")
public class BookController {
    @Autowired
    BookRepository repository;

    @GetMapping
    public ResponseEntity getAllBooks(){
        List<BookResponseDTO> allBooks = this.repository.findAll().stream().map(BookResponseDTO::new).toList();

        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBookById(@PathVariable int id){
        if(this.repository.findById(id).isPresent()){
            Book book = this.repository.findById(id).get();

            return ResponseEntity.ok(book);
        }
        //Mensagem de erro caso não encontre o livro pelo id
        return ResponseEntity.ok(new ErrorResponse("Book not found", HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity postBook(@RequestBody @Valid BookPostDTO data){
        Book book = new Book(data);

        this.repository.save(book);

        return ResponseEntity.ok().build();
    }
}
