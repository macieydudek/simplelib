package pl.com.bottega.simplelib.application;

import pl.com.bottega.simplelib.model.AddBookCommand;
import pl.com.bottega.simplelib.model.BookId;

public interface BookManager {

    BookId add(AddBookCommand cmd);

    void remove(BookId id);

    void listBooks();

    BookDto showDetails(BookId id);
}
