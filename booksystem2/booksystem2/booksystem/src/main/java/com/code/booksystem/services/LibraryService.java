package com.code.booksystem.services;

import com.code.booksystem.Entity.Library;
import com.code.booksystem.dto.BookDto;

import java.util.List;

public interface LibraryService {
    List<Library> getAllBooks();
    Library getBookByIsbn(Long isbn);
    boolean addBook(Library library);
    String updateBook(Long isbn, BookDto bookDto);
    boolean deleteBook(Long isbn);
}
