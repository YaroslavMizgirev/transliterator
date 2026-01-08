package ru.mymsoft.transliterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TransliteratorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransliteratorApiApplication.class, args);
    }

}