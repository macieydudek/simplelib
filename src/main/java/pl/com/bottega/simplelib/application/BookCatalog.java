package pl.com.bottega.simplelib.application;

import pl.com.bottega.simplelib.model.BookId.BookId;

import java.util.List;

public interface BookCatalog {

    List<BookDto> find(BookQuery bookQuery);

    BookDto get(BookId id);

}
