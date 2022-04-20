package ru.otus.spring.kolychev.library.service.impl;

import org.assertj.core.api.Condition;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.kolychev.library.dao.BookAuthorDao;
import ru.otus.spring.kolychev.library.dao.BookDao;
import ru.otus.spring.kolychev.library.dao.BookGenreDao;
import ru.otus.spring.kolychev.library.model.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Сервис для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookService;
    @Captor
    ArgumentCaptor<List<BookGenreRelation>> bookGenreRelationCaptor;
    @Captor
    ArgumentCaptor<List<BookAuthorRelation>> bookAuthorRelationCaptor;
    @Mock
    private BookDao bookDao;
    @Mock
    private BookGenreDao bookGenreDao;
    @Mock
    private BookAuthorDao bookAuthorDao;

    @Test
    @DisplayName("Создать книгу и ее свзи с авторами и жанрами")
    void shouldCreateBookWithAuthorAndGenreRelations() {
        Book book = new Book(UUID.randomUUID().toString(), "title",
                             Set.of(new Author(UUID.randomUUID().toString(), "author1"),
                                    new Author(UUID.randomUUID().toString(), "athor2")),
                             Set.of(new Genre(UUID.randomUUID().toString(), "genre1")));

        bookService.create(book);
        verify(bookDao, times(1)).create(book);
        verify(bookGenreDao, times(1)).create(bookGenreRelationCaptor.capture());
        List<BookGenreRelation> bookGenreRelations = bookGenreRelationCaptor.getValue();
        assertThat(bookGenreRelations).isNotEmpty()
                                      .hasSize(1)
                                      .has(new Condition<>(bgr -> bgr.getBookId().equals(book.getId()), ""),
                                           Index.atIndex(0));
        verify(bookAuthorDao, times(1)).create(bookAuthorRelationCaptor.capture());
        List<BookAuthorRelation> bookAuthorRelations = bookAuthorRelationCaptor.getValue();
        assertThat(bookAuthorRelations).isNotEmpty()
                                       .hasSize(2)
                                       .has(new Condition<>(bar -> bar.getBookId().equals(book.getId()), ""),
                                            Index.atIndex(0))
                                       .has(new Condition<>(bar -> bar.getBookId().equals(book.getId()), ""),
                                            Index.atIndex(1));
    }

    @Test
    @DisplayName("удалить книгу и связи авторов и жанров с этой книгой")
    void shouldDeleteBookAndAuthorsAndGenresRelations() {
        String bookId = UUID.randomUUID().toString();
        bookService.deleteById(bookId);
        verify(bookDao, times(1)).deleteById(bookId);
        verify(bookAuthorDao, times(1)).deleteByBookId(bookId);
        verify(bookGenreDao, times(1)).deleteByBookId(bookId);
    }
}