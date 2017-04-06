package pl.com.bottega.simplelib.infrastructure;

import pl.com.bottega.simplelib.model.Book;
import pl.com.bottega.simplelib.model.BookId.BookId;
import pl.com.bottega.simplelib.model.BookNotFoundException;
import pl.com.bottega.simplelib.model.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class JPQLBookRepository implements BookRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void put(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book get(BookId id) {
        Book book = entityManager.find(Book.class, id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    @Override
    public void remove(BookId id) {
        Query query = entityManager.createQuery("DELETE from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
