package com.pinodev.computer.model;

import java.util.Objects;

public class Pc extends LaptopFactory {
    public static final String TYPE = "Pc";

    private String author;
    private int pages;
    private String isbn;


    public Pc(String title, String author, int year, int pages, String publisher,
              String isbn) {
        super(title, publisher, year);
        this.pages = pages;
        this.author = author;
        this.isbn = isbn;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") +
                getTitle() + ";" +
                getPublisher() + ";" +
                getYear() + ";" +
                author + ";" +
                pages + ";" +
                isbn + "";
    }

    @Override
    public String toString() {
        return super.toString() + ", " + author + ", " + pages + ", " + isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pc pc = (Pc) o;
        return pages == pc.pages &&
                Objects.equals(author, pc.author) &&
                Objects.equals(isbn, pc.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, pages, isbn);
    }
}