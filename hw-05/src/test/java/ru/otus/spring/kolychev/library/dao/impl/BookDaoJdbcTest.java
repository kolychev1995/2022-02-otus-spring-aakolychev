package ru.otus.spring.kolychev.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Genre;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final String EXISTING_BOOK_ID = "2f4e4efd-1b4d-4c44-a091-af4047d60582";
    private static final List<String> EXISTING_BOOK_IDS = List.of("2f4e4efd-1b4d-4c44-a091-af4047d60582",
                                                                  "0efe9df2-31c1-47c8-9c74-8838828d900b",
                                                                  "6976692b-0e40-497a-a984-d863c3468ee9",
                                                                  "f5c2533e-605c-4d7b-b38f-1115af2c9a42");
    private static final Book EXISTING_BOOK = new Book("2f4e4efd-1b4d-4c44-a091-af4047d60582",
                                                       "Мифический человеко-месяц, или Как создаются программные системы",
                                                       Set.of(new Author("92cfd2bd-e57d-495a-8a28-b69157420cc2", "Фредерик Брукс")),
                                                       Set.of(new Genre("eb83b4c6-f80d-49af-ad5b-eda688f78245", "Управление проектами"),
                                                             new Genre("2609013f-573d-4b83-b96e-5f6fd1150de5", "Менеджмент"),
                                                             new Genre("6f9849c2-002c-4ca8-b458-5bb87ff06607", "Разработка ПО")
                                                              ));

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("добавлять книгу в бд")
    void shouldCreateBook() {
        Book expectedBook = new Book(UUID.randomUUID().toString(), "title", Collections.emptySet(), Collections.emptySet());
        bookDaoJdbc.create(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("возвращать книгу по id")
    void shouldReturnBookById() {
        Book actualBook = bookDaoJdbc.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_BOOK);
    }

    @Test
    @DisplayName("возвращать все книги")
    void shouldReturnAllBooks() {
        List<Book> expectedBooks;
        expectedBooks = EXISTING_BOOK_IDS.stream().map(id -> bookDaoJdbc.getById(id)).collect(Collectors.toList());
        List<Book> actualBooks = bookDaoJdbc.getAll();
        assertThat(actualBooks).hasSameElementsAs(expectedBooks);
    }

    @Test
    @DisplayName("обновлять название книги")
    void shouldCorrectUpdateBookTitleById() {
        String expectedNewTitle = "new title";
        bookDaoJdbc.update(new Book(EXISTING_BOOK_ID, expectedNewTitle, Collections.emptySet(), Collections.emptySet()));
        String actualNewTitle = bookDaoJdbc.getById(EXISTING_BOOK_ID).getTitle();
        assertThat(actualNewTitle).isEqualTo(expectedNewTitle);
    }

    @Test
    @DisplayName("удалять книгу по id")
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDaoJdbc.getById(EXISTING_BOOK_ID)).doesNotThrowAnyException();
        bookDaoJdbc.deleteById(EXISTING_BOOK_ID);
        assertThatThrownBy(() -> bookDaoJdbc.getById(EXISTING_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}