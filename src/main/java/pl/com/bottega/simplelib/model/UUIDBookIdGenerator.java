package pl.com.bottega.simplelib.model;


import java.util.UUID;

public class UUIDBookIdGenerator implements BookIdGenerator {
    @Override
    public void generate(Book book) {
        String id = UUID.randomUUID().toString();
        BookId bookId = new BookId(id);
        book.setBookId(bookId);
    }
}
