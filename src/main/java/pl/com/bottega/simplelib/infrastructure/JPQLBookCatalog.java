package pl.com.bottega.simplelib.infrastructure;

import com.sun.deploy.util.StringUtils;
import pl.com.bottega.simplelib.application.*;
import pl.com.bottega.simplelib.model.Author;
import pl.com.bottega.simplelib.model.Book;
import pl.com.bottega.simplelib.model.BookId.BookId;
import pl.com.bottega.simplelib.model.BookNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JPQLBookCatalog implements BookCatalog{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public BookDto get(BookId id) {
        Query query = entityManager.createQuery("SELECT b FROM Book b where b.id = :id");
        query.setParameter("id", id);
        try {
            Book book = (Book) query.getSingleResult();
            return createDto(book);
        } catch (NoResultException ex) {
            throw new BookNotFoundException(id);
        }
    }

    @Override
    public BookSearchResults find(BookQuery bookQuery) {
        BookSearchResults bookSearchResults = new BookSearchResults();
        String jpqlQuery = "SELECT b.author, b.title, b.publishYear," +
                "SUM(CASE b.status WHEN 'AVAILABLE' THEN 1 ELSE 0 END)," +
                "SUM(CASE b.status WHEN 'LEND' THEN 1 ELSE 0 END)" +
                "FROM Book b";
        String countQuery = "SELECT count(b) from Book b";
        Set<String> predicates = createPredicates(bookQuery);
        if (!predicates.isEmpty()) {
            String whereClause = " WHERE " + StringUtils.join(predicates, " AND ");
            jpqlQuery += whereClause;
            countQuery += whereClause;
        }
        jpqlQuery += " group by b.author, b.title, b.publishYear";
        if (bookQuery.getSortBy() != null && bookQuery.getSortOrder() != null) {
            jpqlQuery += " ORDER BY b." + bookQuery.getSortBy() + " " + bookQuery.getSortOrder();
        }
        List<BookRecord> records = createBookRecords(jpqlQuery, bookQuery);
        bookSearchResults.setBookrecords(records);

        Long recordsCount = getTotal(bookQuery, countQuery);
        bookSearchResults.setPerPage(bookQuery.getPerPage());
        bookSearchResults.setPageNumber(bookQuery.getPageNumber());
        bookSearchResults.setPagesCount(countPages(recordsCount, bookQuery));
        return bookSearchResults;
    }



    private int countOffset(BookQuery bookQuery) {
        return (bookQuery.getPageNumber() - 1) * bookQuery.getPerPage();
    }

    private Long countPages(Long recordsCount, BookQuery bookQuery) {
        return recordsCount / bookQuery.getPerPage() + (recordsCount % bookQuery.getPerPage() == 0 ? 0:1);
    }

    private Long getTotal(BookQuery bookQuery, String countQuery) {
        Query count = entityManager.createQuery(countQuery);
        applyPredicateParameters(count, bookQuery);
        return (Long) count.getSingleResult();
    }

    private List<BookRecord> createBookRecords(String jpqlQuery, BookQuery bookQuery) {
        Query query = entityManager.createQuery(jpqlQuery);
        query.setMaxResults(bookQuery.getPerPage());
        query.setFirstResult(countOffset(bookQuery));
        applyPredicateParameters(query, bookQuery);
        List<Object[]> results = query.getResultList();
        List<BookRecord> records = new LinkedList<>();
        for (Object[] o : results) {
            String bookAuthor = o[0].toString();
            String bookTitle = (String) o[1];
            String publishYear = String.valueOf(o[2]);
            long availableCopies = (Long) o[3];
            long lendCopies = (Long) o[4];
            BookRecord bookRecord = new BookRecord();
            bookRecord.setAuthor(bookAuthor);
            bookRecord.setTitle(bookTitle);
            bookRecord.setPublishYear(publishYear);
            bookRecord.setAvailableCopies(availableCopies);
            bookRecord.setLendCopies(lendCopies);
            records.add(bookRecord);
        }
        return records;
    }

    private void applyPredicateParameters(Query query, BookQuery bookQuery) {
        if (bookQuery.getAuthor() != null) {
            query.setParameter("author", new Author(bookQuery.getAuthor()));
        }
        if (bookQuery.getPublishedAfter() != null) {
            query.setParameter("publishedAfter", bookQuery.getPublishedAfter());
        }
        if (bookQuery.getPublishedBefore() != null) {
            query.setParameter("publishedBefore", bookQuery.getPublishedBefore());
        }
        if (bookQuery.getExactTitle() != null) {
            query.setParameter("exactTitle", bookQuery.getExactTitle());
        }
        if (bookQuery.getPhraseInTitle() != null) {
            String likeExpressionUpperCase = "%" + bookQuery.getPhraseInTitle().substring(0,1).toUpperCase() + bookQuery.getPhraseInTitle
                    ().substring(1) + "%";
            String likeExpressionLowerCase = "%" + bookQuery.getPhraseInTitle().substring(0,1).toLowerCase() + bookQuery.getPhraseInTitle
                    ().substring(1) + "%";
            query.setParameter("phraseInTitleUpperCase", likeExpressionUpperCase);
            query.setParameter("phraseInTitleLowerCase", likeExpressionLowerCase);
        }
    }

    private Set<String> createPredicates(BookQuery bookQuery) {
        Set<String> predicates = new HashSet<>();
        if (bookQuery.getAuthor() != null) {
            predicates.add("b.author = :author");
        }
        if (bookQuery.getPublishedAfter() != null) {
            predicates.add("b.publishYear > :publishedAfter");
        }
        if (bookQuery.getPublishedBefore() != null) {
            predicates.add("b.publishYear < :publishedBefore");
        }
        if (bookQuery.getExactTitle() != null) {
            predicates.add("b.title = :exactTitle");
        }
        if (bookQuery.getPhraseInTitle() != null) {
            predicates.add("(b.title LIKE :phraseInTitleUpperCase OR b.title LIKE :phraseInTitleLowerCase)");
            //predicates.add("b.title = :phraseInTitleLowerCase");
        }
        return predicates;
    }

    private BookDto createDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setYear(String.valueOf(book.getPublishYear()));
        bookDto.setAuthor(book.getAuthor().toString());
        if (book.getClient() != null) {
            bookDto.setClient(book.getClient());
        }
        return bookDto;
    }

}
