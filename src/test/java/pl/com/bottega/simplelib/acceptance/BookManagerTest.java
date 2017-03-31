package pl.com.bottega.simplelib.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.bottega.simplelib.application.BookDto;
import pl.com.bottega.simplelib.application.BookManager;
import pl.com.bottega.simplelib.model.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookManagerTest {

    @Autowired
    BookFactory bookFactory;

    @Autowired
    BookManager bookManager;

    @Test
    public void shouldAddBookToRepository() {
        //when - I add Book to library
        BookId bookId = addBookToRepo();
        //then - book can be viewed
        BookDto bookDto = bookManager.showDetails(bookId);
        assertThat(bookDto.getAuthor()).isEqualTo("John Smith");
        assertThat(bookDto.getTitle()).isEqualTo("Bestseller");
        assertThat(bookDto.getYear()).isEqualTo("2017");
    }

    @Test(expected = BookNotFoundException.class)
    public void shouldRemoveBookFromRepository() {
        //given - a book has been added, and then removed from repository
        BookId bookId = addBookToRepo();
        bookManager.remove(bookId);
        //when - I try to view book details
        BookDto bookDto = bookManager.showDetails(bookId);
    }

    @Test
    public void shouldRemoveOnlyOneBookFromRepository() {
        //given - two books have been added, one has been removed
        BookId bookId1 = addBookToRepo();
        AddBookCommand cmd2 = new AddBookCommand();
        BookId bookId2 = bookManager.add(cmd2);
        bookManager.remove(bookId2);
        //when - I try to view the other book details
        BookDto bookDto = bookManager.showDetails(bookId1);
        //then - I get results
        assertThat(bookDto.getAuthor()).isEqualTo("John Smith");
        assertThat(bookDto.getTitle()).isEqualTo("Bestseller");
        assertThat(bookDto.getYear()).isEqualTo("2017");
    }

    private BookId addBookToRepo() {
        AddBookCommand cmd = new AddBookCommand();
        cmd.setAuthor(new Author("John Smith"));
        cmd.setTitle("Bestseller");
        cmd.setPublishYear(2017);
        return bookManager.add(cmd);
    }



}
