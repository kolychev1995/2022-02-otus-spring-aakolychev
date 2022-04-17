package ru.otus.spring.kolychev.library.dao;

import ru.otus.spring.kolychev.library.model.BookGenreRelation;

import java.util.List;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
public interface BookGenreDao {
    void create(List<BookGenreRelation> bookGenreRelation);
    BookGenreRelation getById(String id);
    void deleteByBookId(String bookId);
    int countByGenreId(String id);
}
