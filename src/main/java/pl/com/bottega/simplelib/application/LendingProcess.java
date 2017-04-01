package pl.com.bottega.simplelib.application;

import pl.com.bottega.simplelib.model.BookId.BookId;
import pl.com.bottega.simplelib.model.Client;

import java.util.Collection;

public interface LendingProcess {

    Collection<BookDto> search(BookQuery query);

    void lend(BookId id, Client client);

    void returnBook(BookId id);
}
