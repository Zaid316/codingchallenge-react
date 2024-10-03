package com.code.booksystem;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.code.booksystem.Controller.LibraryController;
import com.code.booksystem.Entity.Library;
import com.code.booksystem.Exceptions.ResourceNotFoundException;
import com.code.booksystem.services.LibraryService;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LibraryControllerTest {

    @Mock
    private LibraryService libSer;

    @InjectMocks
    private LibraryController libraryController;

    @Test
    void getAllBooks() {
        List<Library> books = Arrays.asList(
            new Library(1L, "Book 1", "Author 1", 2000),
            new Library(2L, "Book 2", "Author 2", 2010)
        );

        when(libSer.getAllBooks()).thenReturn(books);
        ResponseEntity<?> response = libraryController.getAllBooks();
        System.out.println("Response Status: " + response.getStatusCode());  
        System.out.println("Response Body: " + response.getBody()); 
        assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    void getBookByIsbn() {
        Long isbn = 1L;
        when(libSer.getBookByIsbn(isbn)).thenReturn(null);

        try {
            libraryController.getBookByIsbn(isbn);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());  // Logs exception message
            assertTrue(e.getMessage().contains("Book with ISBN " + isbn + " not found."));
        }
    }

    @Test
    void addBook() {
        Library newBook = new Library(1L, "New Book", "New Author", 2022);
        when(libSer.addBook(newBook)).thenReturn(true);
        ResponseEntity<String> response = libraryController.addBook(newBook);
        System.out.println("Response Status: " + response.getStatusCode());
        assertTrue(response.getStatusCode() == HttpStatus.CREATED);
    }

    @Test
    void updateBook() {
        Long isbn = 1L;
        Library updatedBook = new Library(1L, "Updated Book", "Updated Author", 2020);
        when(libSer.updateBook(isbn, null)).thenReturn("Book updated successfully.");
        ResponseEntity<String> response = libraryController.updateBook(isbn, null);
        System.out.println("Response: " + response.getBody());
        assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    void deleteBookByIsbn() {
        Long isbn = 1L;
        when(libSer.deleteBook(isbn)).thenReturn(true);
        ResponseEntity<?> response = libraryController.deleteBookByIsbn(isbn);
        System.out.println("Response Status: " + response.getStatusCode());
        assertTrue(response.getStatusCode() == HttpStatus.OK);
    }
}

