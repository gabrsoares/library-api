package com.library_api.controller;

import com.library_api.exception.BookNotFoundException;
import com.library_api.exception.InvalidBodyFormatException;
import com.library_api.model.book.Book;
import com.library_api.model.book.BookPostDTO;
import com.library_api.model.book.BookSelectDTO;
import com.library_api.repository.BookRepository;
import com.library_api.model.book.BookResponseDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;

@RestController()
@RequestMapping("books")
public class BookController {
    @Autowired
    BookRepository repository;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(){
        List<BookResponseDTO> allBooks = this.repository.findAllByOrderByIdAsc().stream().map(BookResponseDTO::new).toList();

        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        if(this.repository.findById(id).isPresent()){
            Book book = this.repository.findById(id).get();

            return ResponseEntity.ok(book);
        }
        //Mensagem de erro caso não encontre o livro pelo id
        throw new BookNotFoundException();

    }

    @PostMapping
    public ResponseEntity postBook(@RequestBody BookPostDTO data){
        try{
            Book book = new Book(data);

            this.repository.save(book);

            return ResponseEntity.ok().build();
        }catch (InputMismatchException ex){
            throw new InvalidBodyFormatException();
        }
    }

    @PutMapping
    public ResponseEntity updateBook(@RequestBody BookSelectDTO data){
        Book book = this.repository.findById(data.id())
                .orElseThrow(BookNotFoundException::new);

        book.updateBook(data);
        this.repository.save(book);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable int id){
        Book book = this.repository.findById(id).orElseThrow(BookNotFoundException::new);
        this.repository.delete(book);

        return ResponseEntity.ok().build();
    }
}
