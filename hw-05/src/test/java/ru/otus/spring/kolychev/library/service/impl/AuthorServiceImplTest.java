package ru.otus.spring.kolychev.library.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.kolychev.library.dao.AuthorDao;
import ru.otus.spring.kolychev.library.dao.BookAuthorDao;
import ru.otus.spring.kolychev.library.dao.impl.AuthorDaoJdbc;
import ru.otus.spring.kolychev.library.dao.impl.BookAuthorDaoJdbc;
import ru.otus.spring.kolychev.library.model.Author;

import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Сервис для работы с авторами должен")
@JdbcTest
@Import({AuthorServiceImpl.class, AuthorDaoJdbc.class, BookAuthorDaoJdbc.class})
class AuthorServiceImplTest {

    private static final String EXISTING_AUTHOR_ID = "92cfd2bd-e57d-495a-8a28-b69157420cc2";
    private static final String EXISTING_AUTHOR_NAME = "Фредерик Брукс";

    @Autowired
    private AuthorServiceImpl authorService;
    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("создать нового автора")
    void shouldCreateNewAuthor() {
        String id = authorService.create("Jon Doe");
        Author expectedAuthor = new Author(id, "Jon Doe");
        Author actualAuthor = authorDao.getById(id);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("вернуть id существующего автора")
    void shouldReturnExistingAuthorIdWithoutCreatingNew() {
        String actualId = authorService.create(EXISTING_AUTHOR_NAME);
        assertThat(actualId).isEqualTo(EXISTING_AUTHOR_ID);
    }


    @Test
    @DisplayName("удалить автора без связей с книгами")
    void shouldDeleteAuthorWithoutBookRelationById() {
        String id = authorService.create("Jon Doe");
        assertThatCode(() -> authorDao.getById(id)).doesNotThrowAnyException();
        authorService.deleteById(id);
        assertThatThrownBy(() -> authorDao.getById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("не удалять автора со связями с книгами")
    void shouldТщеDeleteAuthorWithBookRelationById() {
        authorService.deleteById(EXISTING_AUTHOR_ID);
        assertThat(authorDao.getById(EXISTING_AUTHOR_ID)).usingRecursiveComparison().isEqualTo(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
    }

    @Test
    @DisplayName("вернуть true для существующего автора")
    void shouldReturnTrue() {
        assertThat(authorService.isExists(EXISTING_AUTHOR_NAME)).isEqualTo(true);
    }

    @Test
    @DisplayName("вернуть true для несуществующего автора")
    void shouldReturnFalse() {
        assertThat(authorService.isExists("Jon Doe")).isEqualTo(false);
    }
}