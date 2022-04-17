package ru.otus.spring.kolychev.library.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Genre;
import ru.otus.spring.kolychev.library.service.AuthorService;
import ru.otus.spring.kolychev.library.service.BookService;
import ru.otus.spring.kolychev.library.service.GenreService;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@DisplayName("Сервис библиотеки должен")
@ExtendWith(MockitoExtension.class)
class LibServiceImplTest {
    private static final Book EXISTING_BOOK = new Book("2f4e4efd-1b4d-4c44-a091-af4047d60582",
                                                       "Мифический человеко-месяц, или Как создаются программные " +
                                                       "системы",
                                                       Set.of(new Author("92cfd2bd-e57d-495a-8a28-b69157420cc2",
                                                                         "Фредерик Брукс")),
                                                       Set.of(new Genre("eb83b4c6-f80d-49af-ad5b-eda688f78245",
                                                                        "Управление проектами"),
                                                              new Genre("2609013f-573d-4b83-b96e-5f6fd1150de5",
                                                                        "Менеджмент"),
                                                              new Genre("6f9849c2-002c-4ca8-b458-5bb87ff06607",
                                                                        "Разработка ПО")
                                                       ));
    @Captor
    ArgumentCaptor<Book> bookCaptor;
    @Mock
    private BookService bookService;
    @Mock
    private GenreService genreService;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private LibServiceImpl libService;

    @Test
    @DisplayName("распарсить книгу и создать авторов, жанры и книгу")
    void shouldParseAndCreateBook() {
        String rowBookData = "\"title;author 1,author 2;genre 1,genre 2\"";
        String id = libService.createBook(rowBookData);
        verify(authorService).create("author 1");
        verify(authorService).create("author 2");
        verify(genreService).create("genre 1");
        verify(genreService).create("genre 2");
        verify(bookService).create(bookCaptor.capture());
        Book book = bookCaptor.getValue();
        assertThat(book.getId()).isEqualTo(id);
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
    @DisplayName("удалить книгу, авторов и жанры")
    void shouldDeleteBookById() {
        Mockito.when(bookService.readById(EXISTING_BOOK.getId())).thenReturn(EXISTING_BOOK);
        libService.deleteById(EXISTING_BOOK.getId());
        verify(bookService).deleteById(EXISTING_BOOK.getId());
        EXISTING_BOOK.getGenres().forEach(g -> verify(genreService).deleteById(g.getId()));
        EXISTING_BOOK.getAuthors().forEach(a -> verify(authorService).deleteById(a.getId()));
    }
}