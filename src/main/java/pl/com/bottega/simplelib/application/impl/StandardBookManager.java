package pl.com.bottega.simplelib.application.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.simplelib.application.*;
import pl.com.bottega.simplelib.model.BookRepository;
import pl.com.bottega.simplelib.model.AddBookCommand;
import pl.com.bottega.simplelib.model.Book;
import pl.com.bottega.simplelib.model.BookFactory;
import pl.com.bottega.simplelib.model.BookId.BookId;

import java.util.List;

public class StandardBookManager implements BookManager {

    private BookFactory bookFactory;
    private BookRepository bookRepository;
    private BookCatalog bookCatalog;

    public StandardBookManager(BookFactory bookFactory, BookRepository bookRepository, BookCatalog bookCatalog) {

        this.bookFactory = bookFactory;
        this.bookRepository = bookRepository;
        this.bookCatalog = bookCatalog;
    }

    @Override
    @Transactional
    public BookId add(AddBookCommand cmd) {
        Book book = bookFactory.createBook(cmd);
        bookRepository.put(book);
        return book.getBookId();
    }

    @Override
    @Transactional
    public void remove(BookId id) {
        bookRepository.remove(id);
    }

    @Override
    public BookSearchResults showAllBooks() {
        BookQuery bookQuery = new BookQuery();
        return bookCatalog.find(bookQuery);
    }


    @Override
    public BookDto showDetails(BookId id) {
        return bookCatalog.get(id);
    }
}
