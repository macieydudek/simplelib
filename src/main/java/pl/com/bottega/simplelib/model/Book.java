package pl.com.bottega.simplelib.model;

import pl.com.bottega.simplelib.model.BookId.BookId;

import javax.persistence.*;

@Entity
public class Book {

    @EmbeddedId
    private BookId bookId;
    private String title;
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "author"))
    private Author author;
    private int publishYear;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "client"))
    private Client client;

    Book() {
    }

    Book(AddBookCommand cmd) {
        this.title = cmd.getTitle();
        this.author = cmd.getAuthor();
        this.publishYear = cmd.getPublishYear();
        this.status = BookStatus.AVAILABLE;
    }

    public void lend(Client client) {
        if (this.status.equals(BookStatus.LEND)) {
            throw new BookStatusexception("Book must be AVAILABLE to lend");
        }
        this.client = client;
        this.status = BookStatus.LEND;
    }

    public void returnBook() {
        this.client = null;
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

    public void setBookId(BookId bookId) {
        this.bookId = bookId;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }
}

