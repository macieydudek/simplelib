package pl.com.bottega.simplelib.model.BookId;

import java.io.Serializable;

public class BookId implements Serializable{

    private String id;

    public BookId(String id) {
        this.id = id;
    }

    BookId() {
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookId bookId = (BookId) o;

        return id != null ? id.equals(bookId.id) : bookId.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
