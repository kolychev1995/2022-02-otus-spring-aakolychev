package ru.otus.spring.kolychev.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.kolychev.library.model.Author;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final String EXISTING_AUTHOR_ID = "92cfd2bd-e57d-495a-8a28-b69157420cc2";
    private static final String EXISTING_AUTHOR_NAME = "Фредерик Брукс";

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    @DisplayName("добавлять автора в бд")
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(UUID.randomUUID().toString(), "Jon Doe");
        authorDaoJdbc.create(expectedAuthor);
        Author actualAuthor = authorDaoJdbc.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать автора по id")
    void shouldReturnAuthorById(){
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDaoJdbc.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать автора по имени")
    void shouldReturnAuthorByName() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDaoJdbc.getByName(EXISTING_AUTHOR_NAME);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("удалять автора по id")
    void shouldDeleteById() {
        assertThatCode(() -> authorDaoJdbc.getById(EXISTING_AUTHOR_ID)).doesNotThrowAnyException();
        authorDaoJdbc.deleteById(EXISTING_AUTHOR_ID);
        assertThatThrownBy(() -> authorDaoJdbc.getById(EXISTING_AUTHOR_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("возвращать количество авторов по имени")
    void shouldReturnCountOfAuthors_1() {
        int expectedCountOfAuthors = 1;
        int actualCountOfAuthors = authorDaoJdbc.countByName(EXISTING_AUTHOR_NAME);
        assertThat(actualCountOfAuthors).isEqualTo(expectedCountOfAuthors);
    }

    @Test
    @DisplayName("возвращать количество авторов по имени")
    void shouldReturnCountOfAuthors_0() {
        int expectedCountOfAuthors = 0;
        int actualCountOfAuthors = authorDaoJdbc.countByName("name");
        assertThat(actualCountOfAuthors).isEqualTo(expectedCountOfAuthors);
    }
}