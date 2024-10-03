package com.code.booksystem.services;

import com.code.booksystem.Entity.Library;
import com.code.booksystem.dto.BookDto;
import com.code.booksystem.Exceptions.ResourceNotFoundException;
import com.code.booksystem.Repository.LibraryRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public List<Library> getAllBooks() {
        return libraryRepository.findAll();
    }

    @Override
    public Library getBookByIsbn(Long isbn) {
        return libraryRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found."));
    }

    @Override
    public boolean addBook(Library library) {
        if (libraryRepository.existsByIsbn(library.getIsbn())) {
            return false; // ISBN already exists
        }
        libraryRepository.save(library);
        return true;
    }

    @Override
    public String updateBook(Long isbn, BookDto bookDto) {
        Optional<Library> optionalBook = libraryRepository.findByIsbn(isbn);
        if (optionalBook.isPresent()) {
            Library library = optionalBook.get();
            library.setTitle(bookDto.getTitle());
            library.setAuthor(bookDto.getAuthor());
            library.setYear(bookDto.getYear());
            libraryRepository.save(library);
            return "Book updated successfully.";
        } else {
            return "Book with ISBN " + isbn + " not found.";
        }
    }

    @Override
    public boolean deleteBook(Long isbn) {
        Optional<Library> optionalBook = libraryRepository.findByIsbn(isbn);
        if (optionalBook.isPresent()) {
            libraryRepository.delete(optionalBook.get());
            return true;
        }
        return false; // Book not found
    }
}

