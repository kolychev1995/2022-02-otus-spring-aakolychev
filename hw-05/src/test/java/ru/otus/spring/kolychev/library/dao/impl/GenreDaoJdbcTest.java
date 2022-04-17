package ru.otus.spring.kolychev.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.model.Genre;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final String EXISTING_GENRE_ID = "eb83b4c6-f80d-49af-ad5b-eda688f78245";
    private static final String EXISTING_GENRE_TITLE = "Управление проектами";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("добавлять жанр в бд")
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(UUID.randomUUID().toString(), "Genre");
        genreDaoJdbc.create(expectedGenre);
        Genre actualGenre = genreDaoJdbc.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("возвращать жанр по id")
    void shouldReturnGenreById(){
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TITLE);
        Genre actualGenre = genreDaoJdbc.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    
    @Test
    @DisplayName("возвращать жанр по имени")
    void shouldReturnGenreByName(){
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TITLE);
        Genre actualGenre = genreDaoJdbc.getByTitle(EXISTING_GENRE_TITLE);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("удалять жанр по id")
    void shouldDeleteById() {
        assertThatCode(() -> genreDaoJdbc.getById(EXISTING_GENRE_ID)).doesNotThrowAnyException();
        genreDaoJdbc.deleteById(EXISTING_GENRE_ID);
        assertThatThrownBy(() -> genreDaoJdbc.getById(EXISTING_GENRE_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("возвращать количество жанров по названию")
    void shouldReturnCountOfGenres_1() {
        int expectedCountOfAuthors = 1;
        int actualCountOfAuthors = genreDaoJdbc.countByTitle(EXISTING_GENRE_TITLE);
        assertThat(actualCountOfAuthors).isEqualTo(expectedCountOfAuthors);
    }

    @Test
    @DisplayName("возвращать количество жанров по названию")
    void shouldReturnCountOfGenres_0() {
        int expectedCountOfAuthors = 0;
        int actualCountOfAuthors = genreDaoJdbc.countByTitle("title");
        assertThat(actualCountOfAuthors).isEqualTo(expectedCountOfAuthors);
    }

   
}