package ru.otus.spring.kolychev.library.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.kolychev.library.dao.BookGenreDao;
import ru.otus.spring.kolychev.library.dao.GenreDao;
import ru.otus.spring.kolychev.library.dao.impl.BookGenreDaoJdbc;
import ru.otus.spring.kolychev.library.dao.impl.GenreDaoJdbc;
import ru.otus.spring.kolychev.library.model.Genre;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Genre Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Сервис для работы с жанрами должен")
@JdbcTest
@Import({GenreServiceImpl.class, GenreDaoJdbc.class, BookGenreDaoJdbc.class})
class GenreServiceImplTest {
    private static final String EXISTING_GENRE_ID = "eb83b4c6-f80d-49af-ad5b-eda688f78245";
    private static final String EXISTING_GENRE_TITLE = "Управление проектами";
    
    @Autowired
    private GenreServiceImpl genreService;
    @Autowired
    private  GenreDaoJdbc genreDao;

    @Test
    @DisplayName("создать новый жанр")
    void shouldCreateNewGenre() {
        String id = genreService.create("title");
        Genre expectedGenre = new Genre(id, "title");
        Genre actualGenre = genreDao.getById(id);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("вернуть id существующего жанра")
    void shouldReturnExistingGenreIdWithoutCreatingNew() {
        String actualId = genreService.create(EXISTING_GENRE_TITLE);
        assertThat(actualId).isEqualTo(EXISTING_GENRE_ID);
    }


    @Test
    @DisplayName("удалить жанра без связей с книгами")
    void shouldDeleteGenreWithoutBookRelationById() {
        String id = genreService.create("Jon Doe");
        assertThatCode(() -> genreDao.getById(id)).doesNotThrowAnyException();
        genreService.deleteById(id);
        assertThatThrownBy(() -> genreDao.getById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("не удалять жанр со связями с книгами")
    void shouldТщеDeleteGenreWithBookRelationById() {
        genreService.deleteById(EXISTING_GENRE_ID);
        assertThat(genreDao.getById(EXISTING_GENRE_ID)).usingRecursiveComparison().isEqualTo(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TITLE));
    }

    @Test
    @DisplayName("вернуть true для существующего жанра")
    void shouldReturnTrue() {
        assertThat(genreService.isExists(EXISTING_GENRE_TITLE)).isEqualTo(true);
    }

    @Test
    @DisplayName("вернуть true для несуществующего жанра")
    void shouldReturnFalse() {
        assertThat(genreService.isExists("Jon Doe")).isEqualTo(false);
    }
}