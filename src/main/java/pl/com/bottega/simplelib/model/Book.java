package pl.com.bottega.simplelib.model;

public class Book {

    private BookId bookId;
    private String title;
    private Author author;
    private int publishYear;
    private BookStatus status;
    private Reader reader;

    Book() {
    }

    Book(AddBookCommand cmd) {
        this.title = cmd.getTitle();
        this.author = cmd.getAuthor();
        this.publishYear = cmd.getPublishYear();
        this.status = BookStatus.AVAILABLE;
    }

    public void lent(Reader reader) {
        if (this.status.equals(BookStatus.LEND)) {
            throw new BookStatusexception("Book must be AVAILABLE to lent");
        }
        this.reader = reader;
        this.status = BookStatus.LEND;
    }

    public void returnBook() {
        this.reader = null;
        this.status = BookStatus.AVAILABLE;
    }

    public BookId getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    void setBookId(BookId bookId) {
        this.bookId = bookId;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Reader getReader() {
        return reader;
    }
}

