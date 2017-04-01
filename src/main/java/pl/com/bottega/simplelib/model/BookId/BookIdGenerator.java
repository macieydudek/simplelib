package pl.com.bottega.simplelib.model.BookId;


import pl.com.bottega.simplelib.model.Book;

public interface BookIdGenerator {

    public void generate(Book book);
}