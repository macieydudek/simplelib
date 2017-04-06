package pl.com.bottega.simplelib.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.simplelib.application.BookCatalog;
import pl.com.bottega.simplelib.application.BookDto;
import pl.com.bottega.simplelib.application.BookQuery;
import pl.com.bottega.simplelib.application.BookSearchResults;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JPABookCatalogTest {

    @Autowired
    BookCatalog bookCatalog;


    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindAllBooks() {
        //given
        BookQuery bookQuery = new BookQuery();
        //when
        BookSearchResults results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.getBookrecords().size()).isEqualTo(8);
        assertThat(results.getBookrecords().get(0).getAvailableCopies()).isEqualTo(2);
        assertThat(results.getBookrecords().get(0).getLendCopies()).isEqualTo(1);
        assertThat(results.getBookrecords().get(7).getAvailableCopies()).isEqualTo(0);
        assertThat(results.getBookrecords().get(7).getLendCopies()).isEqualTo(1);
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindBooksByExactTitle() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setExactTitle("Test title");
        //when
        BookSearchResults results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.getBookrecords().size()).isEqualTo(4);
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindBooksByTitlePhrase() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setPhraseInTitle("test");
        //when
        BookSearchResults results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.getBookrecords().size()).isEqualTo(6);
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindBooksByAuthor() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setAuthor("John Smith");
        //when
        BookSearchResults results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.getBookrecords().size()).isEqualTo(4);
    }


    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindBooksByPublishDate() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setPublishedBefore(2017);
        bookQuery.setPublishedAfter(2000);
        //when
        BookSearchResults results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.getBookrecords().size()).isEqualTo(2);
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldReturnPaginatedResults() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setPerPage(2);
        bookQuery.setPageNumber(2);
        //when
        BookSearchResults results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.getBookrecords().size()).isEqualTo(2);
        assertThat(results.getBookrecords().get(1).getAuthor()).isEqualTo("John Smith");
        assertThat(results.getBookrecords().get(1).getTitle()).isEqualTo("Title");
        assertThat(results.getBookrecords().get(1).getPublishYear()).isEqualTo("2017");
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldReturnSortedResults() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setSortBy("publishYear");
        bookQuery.setSortOrder("asc");
        //when
        BookSearchResults results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.getBookrecords().get(0).getPublishYear()).isEqualTo("1999");
        assertThat(results.getBookrecords().get(1).getPublishYear()).isEqualTo("2000");
    }

}
