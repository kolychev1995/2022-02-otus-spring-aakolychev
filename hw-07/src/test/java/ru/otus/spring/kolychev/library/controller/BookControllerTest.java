package ru.otus.spring.kolychev.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.repository.CommentRepository;
import ru.otus.spring.kolychev.library.service.BookService;
import ru.otus.spring.kolychev.library.service.impl.BookServiceImpl;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJpaTest

@Import({BookController.class, BookServiceImpl.class})
class BookControllerTest {

    private static final String CREATE_ERROR_MESSAGE =
            "Ошибка при разборе данных книги. Данные книги должны быть в формате \"title;author1,author2;genre1,genre2\"";
    private static final String EXISTING_BOOK = "Мифический человеко-месяц, или Как создаются программные системы\n" +
                                                "Идентификатор: 1\n" +
                                                "Авторы: Фредерик Брукс\n" +
                                                "Жанры: Управление проектами, Менеджмент, Разработка ПО";
    public static final String EXISTING_BOOK_ID = "1";

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookController bookController;

    @Test
    void shouldReturnBook() {
        String book = bookController.getBook(EXISTING_BOOK_ID);
        assertThat(book).isEqualTo(EXISTING_BOOK);
    }

    @Test
    void shouldReturnNotFoundBookMessage() {
        String book = bookController.getBook("10");
        assertThat(book).isEqualTo("Книга не найдена");
    }

    @Test
    void shouldReturnWrongIdFormatMessage() {
        String book = bookController.getBook("wrong id");
        assertThat(book).isEqualTo("Идентификатор книги должен состоять только из цифр");
    }


    @Test
    void shouldParseAndCreateBook() {
        String rowBookData = "\"title;author 1,author 2;genre 1,genre 2\"";
        String id = bookController.createBook(rowBookData);
        Book book = bookRepository.getById(Long.parseLong(id));
        assertThat(book.getTitle()).isEqualTo("title");
        assertThat(book.getAuthors()).isNotEmpty()
                                     .hasSize(2)
                                     .anyMatch(a -> a.getName().equals("author 1"))
                                     .anyMatch(a -> a.getName().equals("author 2"));
        assertThat(book.getGenres()).isNotEmpty()
                                    .hasSize(2)
                                    .anyMatch(a -> a.getTitle().equals("genre 1"))
                                    .anyMatch(a -> a.getTitle().equals("genre 2"));
    }

    @Test
    void shouldReturnParseErrorMessage() {
        String rowBookData = "\"title;author 1,author 2genre 1,genre 2\"";
        String message = bookController.createBook(rowBookData);
        assertThat(message).isEqualTo(CREATE_ERROR_MESSAGE);
    }

    @Test
    void shouldDeleteBookAndComments() {
        Book book = bookRepository.getById(Long.parseLong(EXISTING_BOOK_ID));
        bookController.deleteBook(EXISTING_BOOK_ID);
        assertThatCode(() -> bookRepository.findById(book.getId()).get()).isInstanceOf(NoSuchElementException.class);
        assertThat(commentRepository.findCommentsByBookId(book.getId())).isEmpty();
    }
}