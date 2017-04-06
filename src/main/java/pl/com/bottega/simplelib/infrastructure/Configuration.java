package pl.com.bottega.simplelib.infrastructure;


import org.springframework.context.annotation.Bean;
import pl.com.bottega.simplelib.application.BookCatalog;
import pl.com.bottega.simplelib.application.BookManager;
import pl.com.bottega.simplelib.model.BookRepository;
import pl.com.bottega.simplelib.application.LendingProcess;
import pl.com.bottega.simplelib.application.impl.StandardBookManager;
import pl.com.bottega.simplelib.application.impl.StandardLendingProcess;
import pl.com.bottega.simplelib.model.BookFactory;
import pl.com.bottega.simplelib.model.BookId.BookIdGenerator;
import pl.com.bottega.simplelib.model.BookId.UUIDBookIdGenerator;

@org.springframework.context.annotation.Configuration
public class Configuration {


    @Bean
    public BookManager bookManager(BookFactory bookFactory, BookRepository bookRepository, BookCatalog bookCatalog) {
        return new StandardBookManager(bookFactory, bookRepository, bookCatalog);
    }

    @Bean BookRepository bookRepository(){
        return new JPQLBookRepository();
    }

    @Bean
    public BookFactory bookFactory(BookIdGenerator bookIdGenerator) {
        return new BookFactory(bookIdGenerator);
    }

    @Bean
    public BookIdGenerator bookIdGenerator() {
        return new UUIDBookIdGenerator();
    }

    @Bean
    public BookCatalog bookCatalog() {
        return new JPQLBookCatalog();}

    @Bean
    public LendingProcess lendingProcess(BookRepository bookRepository, BookCatalog bookCatalog) {
        return new StandardLendingProcess(bookRepository, bookCatalog);
    }
}
