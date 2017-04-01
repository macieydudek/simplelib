package pl.com.bottega.simplelib.application;

import pl.com.bottega.simplelib.model.AddBookCommand;
import pl.com.bottega.simplelib.model.BookId.BookId;

import java.util.List;

public interface BookManager {

    BookId add(AddBookCommand cmd);

    void remove(BookId id);

    List<String> listBooks();

    BookDto showDetails(BookId id);
}
