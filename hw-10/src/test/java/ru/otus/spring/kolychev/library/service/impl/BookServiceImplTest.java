package ru.otus.spring.kolychev.library.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kolychev.library.controller.BookController;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataMongoTest()
@Import({BookController.class, BookServiceImpl.class, CommentServiceImpl.class})
class BookServiceImplTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookServiceImpl bookService;
    private static final List<String> authors = List.of("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10");

    @Test
    void shouldReturnAllAuthorsInLib() {
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findAll()).thenReturn(generateBooks());
        BookServiceImpl bookService = new BookServiceImpl(bookRepository, mock(CommentService.class));

        List<String> expectedBooks = authors.stream()
                                            .map(a -> a.toUpperCase(Locale.ROOT))
                                            .collect(Collectors.toList());
        assertThat(bookService.getAllAuthors()).hasSameElementsAs(expectedBooks);
    }

    @Test
    void shouldReturnBook() {
        List<Book> books = bookService.getBookByAuthor("Таненбаум Эндрю");
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("СОВРЕМЕННЫЕ ОПЕРАЦИОННЫЕ СИСТЕМЫ");
    }

    private List<Book> generateBooks() {
        List<Book> books = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < 10; i++) {
            books.add(new Book(String.valueOf(i), List.of(authors.get(i),
                                                          authors.get(9 - i)),
                                                          List.of("Gennre")));
        }
        return books;
    }
}