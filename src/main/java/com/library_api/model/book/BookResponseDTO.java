package com.library_api.model.book;

public record BookResponseDTO(int id, String title, String author, int year) {
    public BookResponseDTO(Book book){
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
    }
}
