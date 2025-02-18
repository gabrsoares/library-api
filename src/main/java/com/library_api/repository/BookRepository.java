package com.library_api.repository;

import com.library_api.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByTitle (String title);
    List<Book> findByAuthor (String author);
    List<Book> findByYear (int year);
}
