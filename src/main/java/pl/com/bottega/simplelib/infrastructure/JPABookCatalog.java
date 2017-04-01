package pl.com.bottega.simplelib.infrastructure;

import pl.com.bottega.simplelib.application.BookCatalog;
import pl.com.bottega.simplelib.application.BookDto;
import pl.com.bottega.simplelib.application.BookQuery;
import pl.com.bottega.simplelib.model.Book;
import pl.com.bottega.simplelib.model.BookId.BookId;
import pl.com.bottega.simplelib.model.BookNotFoundException;
import pl.com.bottega.simplelib.model.BookStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JPABookCatalog implements BookCatalog{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<BookDto> find(BookQuery bookQuery) {
        List<BookDto> results = new LinkedList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        Set<Predicate> predicates = createPredicates(bookQuery, criteriaBuilder, root);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));

        Query query = entityManager.createQuery(criteriaQuery);

        List<Book> queryResults = query.getResultList();

        for (Book book : queryResults) {
            BookDto bookDto = createDto(book);
            results.add(bookDto);
        }
        return results;
    }

    private Set<Predicate> createPredicates(BookQuery bookQuery, CriteriaBuilder criteriaBuilder, Root<Book> root) {
        Set<Predicate> predicates = new HashSet<>();
        addOnlyAvailablePredicate(criteriaBuilder, root, predicates);
        addExactTitlePredicate(bookQuery, criteriaBuilder, root, predicates);
        addPhraseInTitlePredicate(bookQuery, criteriaBuilder, root, predicates);
        addAuthorPredicate(bookQuery, criteriaBuilder, root, predicates);
        addPublishedAfterPredicate(bookQuery, criteriaBuilder, root, predicates);
        addPublishedBeforePredicate(bookQuery, criteriaBuilder, root, predicates);
        return predicates;
    }

    private void addPublishedBeforePredicate(BookQuery bookQuery, CriteriaBuilder criteriaBuilder, Root<Book> root, Set<Predicate> predicates) {
        if (bookQuery.getPublishedBefore() != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("publishYear"), bookQuery.getPublishedBefore()));
        }
    }

    private void addPublishedAfterPredicate(BookQuery bookQuery, CriteriaBuilder criteriaBuilder, Root<Book> root, Set<Predicate> predicates) {
        if (bookQuery.getPublishedAfter() != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("publishYear"), bookQuery.getPublishedAfter()));
        }
    }

    private void addAuthorPredicate(BookQuery bookQuery, CriteriaBuilder criteriaBuilder, Root<Book> root, Set<Predicate> predicates) {
        if (bookQuery.getAuthor() != null) {
            predicates.add(criteriaBuilder.equal(root.get("author").get("name"), bookQuery.getAuthor()));
        }
    }

    private void addPhraseInTitlePredicate(BookQuery bookQuery, CriteriaBuilder criteriaBuilder, Root<Book> root, Set<Predicate> predicates) {
        String phrase = bookQuery.getPhraseInTitle();
        if (phrase != null) {
            String phraseStartingWithUpperCase = phrase.substring(0, 1).toUpperCase() + phrase.substring(1);
            String phraseStartingWithLowerCase = phrase.substring(0, 1).toLowerCase() + phrase.substring(1);
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" + phraseStartingWithUpperCase + "%"),
                    criteriaBuilder.like(root.get("title"), "%" + phraseStartingWithLowerCase + "%")
            ));
        }
    }

    private void addExactTitlePredicate(BookQuery bookQuery, CriteriaBuilder criteriaBuilder, Root<Book> root, Set<Predicate> predicates) {
        if (bookQuery.getExactTitle() != null) {
            predicates.add(criteriaBuilder.equal(root.get("title"), bookQuery.getExactTitle()));
        }
    }

    private void addOnlyAvailablePredicate(CriteriaBuilder criteriaBuilder, Root<Book> root, Set<Predicate> predicates) {
        predicates.add(criteriaBuilder.equal(root.get("status"), BookStatus.AVAILABLE));
    }

    @Override
    public BookDto get(BookId id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("bookId"), id));
        Query query = entityManager.createQuery(criteriaQuery);
        try {
            Book book = (Book) query.getSingleResult();
            BookDto bookDto = createDto(book);
            includeClient(bookDto, book);
            return bookDto;
        } catch (NoResultException ex) {
            throw new BookNotFoundException(id);
        }

    }

    private void includeClient(BookDto bookDto, Book book) {
        bookDto.setClient(book.getClient());
    }

    private BookDto createDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setYear(String.valueOf(book.getPublishYear()));
        bookDto.setAuthor(book.getAuthor().toString());
        return bookDto;
    }
}
