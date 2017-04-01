package pl.com.bottega.simplelib.application.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.simplelib.application.BookDto;
import pl.com.bottega.simplelib.application.BookQuery;
import pl.com.bottega.simplelib.model.BookRepository;
import pl.com.bottega.simplelib.application.LendingProcess;
import pl.com.bottega.simplelib.model.Book;
import pl.com.bottega.simplelib.model.BookId.BookId;
import pl.com.bottega.simplelib.model.Client;

import java.util.Collection;

public class StandardLendingProcess implements LendingProcess{

    private BookRepository bookRepository;

    public StandardLendingProcess(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<BookDto> search(BookQuery query) {
        return null;
    }

    @Override
    @Transactional
    public void lend(BookId id, Client client) {
        Book book = bookRepository.get(id);
        book.lend(client);
    }

    @Override
    public void returnBook(BookId id) {

    }
}
