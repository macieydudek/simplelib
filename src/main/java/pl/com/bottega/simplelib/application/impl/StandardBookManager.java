package pl.com.bottega.simplelib.application.impl;

import pl.com.bottega.simplelib.application.BookDto;
import pl.com.bottega.simplelib.application.BookManager;
import pl.com.bottega.simplelib.model.AddBookCommand;
import pl.com.bottega.simplelib.model.BookId;

public class StandardBookManager implements BookManager {

    @Override
    public BookId add(AddBookCommand cmd) {
        return null;
    }

    @Override
    public void remove(BookId id) {

    }

    @Override
    public void listBooks() {

    }

    @Override
    public BookDto showDetails(BookId id) {
        return null;
    }
}
