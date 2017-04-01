package pl.com.bottega.simplelib.model;

import pl.com.bottega.simplelib.model.BookId.BookId;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(BookId id) {
        super(String.format("Book nr: %s not found", id.getId()));
    }
}
