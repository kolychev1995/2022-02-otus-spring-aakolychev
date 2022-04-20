package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kolychev.library.dao.BookAuthorDao;
import ru.otus.spring.kolychev.library.dao.BookDao;
import ru.otus.spring.kolychev.library.dao.BookGenreDao;
import ru.otus.spring.kolychev.library.model.*;
import ru.otus.spring.kolychev.library.service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final BookGenreDao bookGenreDao;
    private final BookAuthorDao bookAuthorDao;

    @Override
    public void create(Book book) {
        bookDao.create(book);
        List<BookGenreRelation> bookGenreRelations = new ArrayList<>();
        for (Genre genre : book.getGenres()) {
            BookGenreRelation bookGenreRelation = new BookGenreRelation(UUID.randomUUID().toString(),
                                                                        book.getId(), genre.getId());
            bookGenreRelations.add(bookGenreRelation);
        }
        List<BookAuthorRelation> bookAuthorRelations = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            BookAuthorRelation bookAuthorRelation = new BookAuthorRelation(UUID.randomUUID().toString(),
                                                                           book.getId(), author.getId());
            bookAuthorRelations.add(bookAuthorRelation);
        }
        bookGenreDao.create(bookGenreRelations);
        bookAuthorDao.create(bookAuthorRelations);
    }

    @Override
    public Book readById(String id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void updateTitle(String id, String title) {
        bookDao.update(new Book(id, title, Collections.emptySet(), Collections.emptySet()));
    }

    @Override
    public void deleteById(String id) {
        bookDao.deleteById(id);
        bookAuthorDao.deleteByBookId(id);
        bookGenreDao.deleteByBookId(id);
    }

}
