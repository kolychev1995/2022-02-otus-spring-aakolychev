package ru.otus.spring.kolychev.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Comment;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.repository.CommentRepository;
import ru.otus.spring.kolychev.library.service.impl.BookServiceImpl;
import ru.otus.spring.kolychev.library.service.impl.CommentServiceImpl;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataMongoTest()
@Import({BookController.class, BookServiceImpl.class, CommentServiceImpl.class})
class BookControllerTest {

    private static final String CREATE_ERROR_MESSAGE =
            "Ошибка при разборе данных книги. Данные книги должны быть в формате \"title;author1,author2;genre1,genre2\"";
    private static final String EXISTING_BOOK = "МИФИЧЕСКИЙ ЧЕЛОВЕКО-МЕСЯЦ, ИЛИ КАК СОЗДАЮТСЯ ПРОГРАММНЫЕ СИСТЕМЫ\n" +
                                                "Идентификатор: %s\n" +
                                                "Авторы: ФРЕДЕРИК БРУКС\n" +
                                                "Жанры: УПРАВЛЕНИЕ ПРОЕКТАМИ, МЕНЕДЖМЕНТ, РАЗРАБОТКА ПО";
    public static String EXISTING_BOOK_ID;
    public static final String EXISTING_BOOK_TITLE = "МИФИЧЕСКИЙ ЧЕЛОВЕКО-МЕСЯЦ, ИЛИ КАК СОЗДАЮТСЯ ПРОГРАММНЫЕ СИСТЕМЫ";

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookController bookController;

    @BeforeEach
    void init() {
        Book exBook = new Book();
        exBook.setTitle(EXISTING_BOOK_TITLE);
        Book book = bookRepository.findOne(Example.of(exBook)).orElseThrow();
        EXISTING_BOOK_ID = book.getId();
    }

    @Test
    void shouldReturnBook() {
        String book = bookController.getBook(EXISTING_BOOK_ID);
        assertThat(book).isEqualTo(EXISTING_BOOK, EXISTING_BOOK_ID);
    }

    @Test
    void shouldReturnNotFoundBookMessage() {
        String book = bookController.getBook("10");
        assertThat(book).isEqualTo("Книга не найдена");
    }

    @Test
    void shouldParseAndCreateBook() {
        String rowBookData = "\"title;author 1,author 2;genre 1,genre 2\"";
        String id = bookController.createBook(rowBookData);
        Book book = bookRepository.findById(id).orElseThrow();
        assertThat(book.getTitle()).isEqualTo("TITLE");
        assertThat(book.getAuthors()).isNotEmpty()
                                     .hasSize(2)
                                     .anyMatch(a -> a.equals("AUTHOR 1"))
                                     .anyMatch(a -> a.equals("AUTHOR 2"));
        assertThat(book.getGenres()).isNotEmpty()
                                    .hasSize(2)
                                    .anyMatch(a -> a.equals("GENRE 1"))
                                    .anyMatch(a -> a.equals("GENRE 2"));
    }

    @Test
    void shouldReturnParseErrorMessage() {
        String rowBookData = "\"title;author 1,author 2genre 1,genre 2\"";
        String message = bookController.createBook(rowBookData);
        assertThat(message).isEqualTo(CREATE_ERROR_MESSAGE);
    }

    @Test
    @DirtiesContext
    void shouldDeleteBookAndComments() {
        Book book = bookRepository.findById(EXISTING_BOOK_ID).orElseThrow();
        bookController.deleteBook(EXISTING_BOOK_ID);
        assertThatCode(() -> bookRepository.findById(book.getId()).orElseThrow()).isInstanceOf(NoSuchElementException.class);
        Comment comment = new Comment();
        comment.setBookId(EXISTING_BOOK_ID);
        List<Comment> comments = commentRepository.findAll(Example.of(comment, ExampleMatcher.matchingAll().withIgnorePaths("id")));
        assertThat(comments).isEmpty();
    }
}