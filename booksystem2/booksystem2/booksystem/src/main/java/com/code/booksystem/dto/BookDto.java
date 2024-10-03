package com.code.booksystem.dto;

public class BookDto {
    private String author;
    private String title;
    private Integer year;

    // Getters and Setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() { // Changed from getName to getTitle
        return title;
    }

    public void setTitle(String title) { // Changed from setName to setTitle
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}



