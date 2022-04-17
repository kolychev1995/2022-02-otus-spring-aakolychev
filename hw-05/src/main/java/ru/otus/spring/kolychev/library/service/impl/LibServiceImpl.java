package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Genre;
import ru.otus.spring.kolychev.library.model.RowBook;
import ru.otus.spring.kolychev.library.service.AuthorService;
import ru.otus.spring.kolychev.library.service.BookService;
import ru.otus.spring.kolychev.library.service.GenreService;
import ru.otus.spring.kolychev.library.service.LibService;

import java.util.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Service
@RequiredArgsConstructor
public class LibServiceImpl implements LibService {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Override
    public String createBook(String rowBookData) {
        RowBook rowBook = parseBook(rowBookData);
        return createBook(rowBook);
    }

    @Override
    public Book readById(String id) {
        return bookService.readById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @Override
    public void updateBookTitle(String id, String title) {
        bookService.updateTitle(id, title);
    }

    @Override
    public void deleteById(String id) {
        Book book = bookService.readById(id);
        bookService.deleteById(id);
        book.getGenres().forEach(genre -> genreService.deleteById(genre.getId()));
        book.getAuthors().forEach(author -> authorService.deleteById(author.getId()));
    }

    private RowBook parseBook(String rowBookData) {
        String[] data = rowBookData.replace("\"", "")
                                   .split(";");
        List<String> authors = Arrays.asList(data[1].split(","));
        List<String> genres = Arrays.asList(data[2].split(","));
        return new RowBook(data[0], authors, genres);
    }

    private String createBook(RowBook rowBook) {
        Set<Author> authors = new HashSet<>();
        rowBook.getAuthors().forEach(author -> authors.add(new Author(authorService.create(author), author)));
        Set<Genre> genres = new HashSet<>();
        rowBook.getGenres().forEach(genre -> genres.add(new Genre(genreService.create(genre), genre)));

        Book newBook = new Book(UUID.randomUUID().toString(), rowBook.getTitle(), authors, genres);
        bookService.create(newBook);
        return newBook.getId();
    }

}
