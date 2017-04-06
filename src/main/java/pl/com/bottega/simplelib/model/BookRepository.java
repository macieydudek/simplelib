package pl.com.bottega.simplelib.model;

import pl.com.bottega.simplelib.model.BookId.BookId;

import java.util.List;

public interface BookRepository {
    void put(Book book);

    Book get(BookId id);

    void remove(BookId id);
}
