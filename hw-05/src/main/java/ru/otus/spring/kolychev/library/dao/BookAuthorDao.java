package ru.otus.spring.kolychev.library.dao;

import ru.otus.spring.kolychev.library.model.BookAuthorRelation;

import java.util.List;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
public interface BookAuthorDao {
    void create(List<BookAuthorRelation> relations);
    BookAuthorRelation getById(String id);
    void deleteByBookId(String bookId);
    int countByAuthorId(String id);
}
