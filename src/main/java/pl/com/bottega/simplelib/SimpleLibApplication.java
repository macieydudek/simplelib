package pl.com.bottega.simplelib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SimpleLibApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SimpleLibApplication.class, args);
    }
}
