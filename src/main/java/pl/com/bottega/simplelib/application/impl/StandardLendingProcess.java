package pl.com.bottega.simplelib.application.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.simplelib.application.*;
import pl.com.bottega.simplelib.model.BookRepository;
import pl.com.bottega.simplelib.model.Book;
import pl.com.bottega.simplelib.model.BookId.BookId;
import pl.com.bottega.simplelib.model.Client;

import java.util.Collection;

public class StandardLendingProcess implements LendingProcess {

    private BookRepository bookRepository;
    private BookCatalog bookCatalog;

    public StandardLendingProcess(BookRepository bookRepository, BookCatalog bookCatalog) {

        this.bookRepository = bookRepository;
        this.bookCatalog = bookCatalog;
    }

    @Override
    public BookSearchResults search(BookQuery query) {
        return bookCatalog.find(query);
    }

    @Override
    @Transactional
    public void lend(BookId id, Client client) {
        Book book = bookRepository.get(id);
        book.lend(client);
    }

    @Override
    @Transactional
    public void returnBook(BookId id) {
        Book book = bookRepository.get(id);
        book.returnBook();
    }
}
