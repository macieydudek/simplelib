package pl.com.bottega.simplelib.model;

public class BookFactory {

    private BookIdGenerator bookIdGenerator;

    public BookFactory(BookIdGenerator bookIdGenerator) {

        this.bookIdGenerator = bookIdGenerator;
    }

    public Book createBook(AddBookCommand cmd) {
        Book book = new Book(cmd);
        bookIdGenerator.generate(book);
        return book;
    }

}
