package ru.otus.spring.kolychev.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.kolychev.library.model.BookAuthorRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Dao для работы со связью между книгами и авторами должно")
@JdbcTest
@Import(BookAuthorDaoJdbc.class)
class BookAuthorDaoJdbcTest {

    private static final String EXISTING_AUTHOR_ID = "5bca5bfd-53ae-4ee4-b251-1f92fb9b78ad";
    private static final String EXISTING_BOOK_ID = "6976692b-0e40-497a-a984-d863c3468ee9";
    private static final int COUNT_OF_EXISTING_BOOK_AUTHOR_RELATION = 2;
    private static final List<String> EXISTING_BOOK_AUTHOR_RELATION_IDS = List.of("25793836-3a2a-48c0-8b1b-aa229867e7ce",
                                                                                  "1d4a4fb5-4696-4af2-95ce-a5a4348f50fa");
    private static final BookAuthorRelation EXISTING_BOOK_AUTHOR_RELATION = new BookAuthorRelation("f395cd09-ad00-46c1-a040-c450ecf5a5d2",
                                                                                                   "2f4e4efd-1b4d-4c44-a091-af4047d60582",
                                                                                                   "92cfd2bd-e57d-495a-8a28-b69157420cc2");

    @Autowired
    private BookAuthorDaoJdbc bookAuthorDaoJdbc;

    @Test
    @DisplayName("создавать связь между книгой и автором")
    void shouldCreateRelation() {
        List<BookAuthorRelation> expectedRelations = List.of( new BookAuthorRelation(UUID.randomUUID().toString(),
                                                                               UUID.randomUUID().toString(),
                                                                               UUID.randomUUID().toString()),
                                                                       new BookAuthorRelation(UUID.randomUUID().toString(),
                                                                                              UUID.randomUUID().toString(),
                                                                                              UUID.randomUUID().toString()));
        bookAuthorDaoJdbc.create(expectedRelations);
        List<BookAuthorRelation> actualRelations = new ArrayList<>();
        actualRelations.add(bookAuthorDaoJdbc.getById(expectedRelations.get(0).getId()));
        actualRelations.add(bookAuthorDaoJdbc.getById(expectedRelations.get(1).getId()));

        assertThat(actualRelations).hasSameElementsAs(expectedRelations);
    }

    @Test
    @DisplayName("удалять связь между книгой и автором по id книги")
    void shouldReturnRelationById() {
        BookAuthorRelation actualRelation = bookAuthorDaoJdbc.getById(EXISTING_BOOK_AUTHOR_RELATION.getId());
        assertThat(actualRelation).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_AUTHOR_RELATION);
    }

    @Test
    @DisplayName("удалять связь между книгой и автором по id")
    void shouldDeleteRelationByBookId() {
        EXISTING_BOOK_AUTHOR_RELATION_IDS.forEach(id -> assertThatCode(() -> bookAuthorDaoJdbc.getById(id)).doesNotThrowAnyException());
        bookAuthorDaoJdbc.deleteByBookId(EXISTING_BOOK_ID);
        EXISTING_BOOK_AUTHOR_RELATION_IDS.forEach(id -> assertThatThrownBy(()-> bookAuthorDaoJdbc.getById(id)).isInstanceOf(
                EmptyResultDataAccessException.class));
    }

    @Test
    @DisplayName("возвращать количество связей между книгой и автором по id автора")
    void shouldReturnCountORelationByAuthorId() {
        int actualCountORelation = bookAuthorDaoJdbc.countByAuthorId(EXISTING_AUTHOR_ID);
        assertThat(actualCountORelation).isEqualTo(COUNT_OF_EXISTING_BOOK_AUTHOR_RELATION);
    }
}