package com.code.booksystem.Controller;

import com.code.booksystem.Entity.Library;
import com.code.booksystem.dto.BookDto;
import com.code.booksystem.Exceptions.ResourceNotFoundException;
import com.code.booksystem.services.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:3000")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/books")
    public ResponseEntity<List<Library>> getAllBooks() {
        List<Library> books = libraryService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<Library> getBookByIsbn(@PathVariable Long isbn) {
        Library book = libraryService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody Library library) {
        boolean isSuccessful = libraryService.addBook(library);
        if (isSuccessful) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add the book. ISBN might already exist.");
        }
    }

    @PutMapping("/updateBook/{isbn}")
    public ResponseEntity<String> updateBook(@PathVariable Long isbn, @RequestBody BookDto bookDto) {
        String resultMessage = libraryService.updateBook(isbn, bookDto);
        if (resultMessage.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
        } else {
            throw new ResourceNotFoundException(resultMessage);
        }
    }

    @DeleteMapping("/deleteBook/{isbn}")
    public ResponseEntity<String> deleteBookByIsbn(@PathVariable Long isbn) {
        boolean isDeleted = libraryService.deleteBook(isbn);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Book with ISBN " + isbn + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ISBN " + isbn + " not found.");
        }
    }
}
