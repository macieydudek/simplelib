package pl.com.bottega.simplelib.model;


import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {


    // ---1---
    @Test
    public void shouldHaveIdAfterCreation() {
        Book book = given().createBook();
        assertNotNull(book.getBookId());
    }

    // ---2---
    @Test
    public void shouldRememberTitle() {
        Book book = given().createBook();
        assertEquals("God save Harambe!", book.getTitle());
    }

    // ---3---
    @Test
    public void shouldRememberAuthor() {
        Book book = given().createBook();
        assertEquals(new Author("Roland Deschain"), book.getAuthor());
    }

    // ---4---
    @Test
    public void shouldRememberPublishYear() {
        Book book = given().createBook();
        assertTrue(book.getPublishYear() == 2017);
    }

    // ---5---
    @Test
    public void shouldHaveAVAILABLESTATUSOnCreation() {
        Book book = given().createBook();
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    // ---6---
    @Test
    public void shouldHaveLENDStatusOnLending() {
        Book book = given().lendBook();
        assertEquals(BookStatus.LEND, book.getStatus());
    }

    // ---7---
    @Test
    public void shouldHaveAVAILABLEStatusAfterReturning() {
        //given
        Book book = given().lendBook();
        //when
        book.returnBook();
        //then
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    // ---8---
    @Test(expected = BookStatusexception.class)
    public void shouldNotAllowToLendIfBookIsLEND() {
        //given
        Book book = given().lendBook();
        book.lent(new Reader("Joe Normal"));
    }

    // ---9---
    @Test
    public void shouldRememberCurrentReader() {
        Book book = given().lendBook();
        assertEquals(new Reader("Joe Normal"), book.getReader());
    }

    private BookAssembler given() {
        return new BookAssembler();
    }

    // ---10---
    @Test
    public void ShouldHaveNoReaderAfterReturningBook() {
        //given
        Book book = given().lendBook();
        //when
        book.returnBook();
        //then
        assertNull(book.getReader());
    }

    class BookAssembler {

        public Book createBook() {
            AddBookCommand cmd = new AddBookCommand();
            cmd.setTitle("God save Harambe!");
            cmd.setPublishYear(2017);
            cmd.setAuthor(new Author("Roland Deschain"));
            BookIdGenerator bookIdGenerator = new UUIDBookIdGenerator();
            BookFactory bookFactory = new BookFactory(bookIdGenerator);
            Book book = bookFactory.createBook(cmd);
            return book;
        }

        public Book lendBook() {
            Book book = createBook();
            book.lent(new Reader("Joe Normal"));
            return book;
        }
    }
}
