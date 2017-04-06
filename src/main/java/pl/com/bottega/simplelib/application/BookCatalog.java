package pl.com.bottega.simplelib.application;

import pl.com.bottega.simplelib.model.BookId.BookId;

import java.util.List;

public interface BookCatalog {

    BookSearchResults find(BookQuery bookQuery);

    BookDto get(BookId id);

}
