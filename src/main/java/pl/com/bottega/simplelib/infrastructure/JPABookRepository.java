package pl.com.bottega.simplelib.infrastructure;

import pl.com.bottega.simplelib.model.BookRepository;
import pl.com.bottega.simplelib.model.Book;
import pl.com.bottega.simplelib.model.BookId.BookId;
import pl.com.bottega.simplelib.model.BookNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;

public class JPABookRepository implements BookRepository {

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Book> delete = criteriaBuilder.createCriteriaDelete(Book.class);
        Root<Book> root = delete.from(Book.class);
        delete.where(criteriaBuilder.equal(root.get("bookId"), id));
        Query query = entityManager.createQuery(delete);
        int rowCount = query.executeUpdate();
    }

    @Override
    public List<String> listDistinctBooks() {
        return null;
    }
}
