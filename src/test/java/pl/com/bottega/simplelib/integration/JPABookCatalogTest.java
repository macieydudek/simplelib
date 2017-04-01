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
    public void shouldFindOnlyAvailableBooks() {
        //given
        BookQuery bookQuery = new BookQuery();
        //when
        List<BookDto> results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.size()).isEqualTo(8);
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindBooksByExactTitle() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setExactTitle("Test title");
        //when
        List<BookDto> results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.size()).isEqualTo(4);
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindBooksByTitlePhrase() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setPhraseInTitle("test");
        //when
        List<BookDto> results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.size()).isEqualTo(6);
    }

    @Test
    @Sql("/fixtures/books.sql")
    @Transactional
    public void shouldFindBooksByAuthor() {
        //given
        BookQuery bookQuery = new BookQuery();
        bookQuery.setAuthor("John Smith");
        //when
        List<BookDto> results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.size()).isEqualTo(5);
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
        List<BookDto> results = bookCatalog.find(bookQuery);
        //then
        assertThat(results.size()).isEqualTo(2);
    }
}
