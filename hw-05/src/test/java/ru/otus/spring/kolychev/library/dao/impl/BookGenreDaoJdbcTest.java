package ru.otus.spring.kolychev.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.kolychev.library.model.BookGenreRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Dao для работы со связью между книгами и жанрами должно")
@JdbcTest
@Import(BookGenreDaoJdbc.class)
class BookGenreDaoJdbcTest {

    private static final String EXISTING_GENRE_ID = "49f4c4f3-420d-4963-ac99-6714e8aa74be";
    private static final int COUNT_OF_EXISTING_BOOK_GENRE_RELATION = 2;
    private static final String EXISTING_BOOK_ID = "6976692b-0e40-497a-a984-d863c3468ee9";
    private static final List<String> EXISTING_BOOK_GENRE_RELATION_IDS = List.of("e08e2a87-4b06-4558-8aa7-c7093d3e4e2d",
                                                                                 "5fe200d3-c6e7-4ba5-af1e-c386592b4f7f");
    private static final BookGenreRelation EXISTING_BOOK_AUTHOR_RELATION =
            new BookGenreRelation("01e221db-ccf1-4724-a49d-694f2d3c0198",
                                  "2f4e4efd-1b4d-4c44-a091-af4047d60582",
                                  "eb83b4c6-f80d-49af-ad5b-eda688f78245");

    @Autowired
    private BookGenreDaoJdbc bookGenreDao;

    @Test
    @DisplayName("добавлять связь между книгой и жанром в бд")
    void shouldCreateBookGenreRelation() {
        List<BookGenreRelation> expectedRelations = List.of(new BookGenreRelation(UUID.randomUUID().toString(),
                                                                                  UUID.randomUUID().toString(),
                                                                                  UUID.randomUUID().toString()),
                                                            new BookGenreRelation(UUID.randomUUID().toString(),
                                                                                  UUID.randomUUID().toString(),
                                                                                  UUID.randomUUID().toString()));
        bookGenreDao.create(expectedRelations);
        List<BookGenreRelation> actualRelations = new ArrayList<>();
        actualRelations.add(bookGenreDao.getById(expectedRelations.get(0).getId()));
        actualRelations.add(bookGenreDao.getById(expectedRelations.get(1).getId()));

        assertThat(actualRelations).hasSameElementsAs(expectedRelations);
    }

    @Test
    @DisplayName("удалять связь между книгой и жанром по id")
    void shouldReturnRelationById() {
        BookGenreRelation actualRelation = bookGenreDao.getById(EXISTING_BOOK_AUTHOR_RELATION.getId());
        assertThat(actualRelation).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_AUTHOR_RELATION);
    }

    @Test
    @DisplayName("удалять связь между книгой и жанром по id книги")
    void shouldDeleteRelationByBookId() {
        EXISTING_BOOK_GENRE_RELATION_IDS.forEach(id -> assertThatCode(() -> bookGenreDao.getById(id)).doesNotThrowAnyException());
        bookGenreDao.deleteByBookId(EXISTING_BOOK_ID);
        EXISTING_BOOK_GENRE_RELATION_IDS.forEach(id -> assertThatThrownBy(()-> bookGenreDao.getById(id)).isInstanceOf(
                EmptyResultDataAccessException.class));
    }

    @Test
    @DisplayName("возвращать количество связей между книгой и автором по id автора")
    void shouldReturnCountORelationByAuthorId() {
        int actualCountORelation = bookGenreDao.countByGenreId(EXISTING_GENRE_ID);
        assertThat(actualCountORelation).isEqualTo(COUNT_OF_EXISTING_BOOK_GENRE_RELATION);
    }
}