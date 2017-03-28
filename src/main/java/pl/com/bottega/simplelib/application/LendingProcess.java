package pl.com.bottega.simplelib.application;

import pl.com.bottega.simplelib.model.BookId;

import java.util.Collection;

public interface LendingProcess {

    Collection<BookDto> search(BookQuery query);

    void lend(BookId id);

    void returnBook(BookId id);
}
