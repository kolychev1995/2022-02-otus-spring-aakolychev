package ru.otus.spring.kolychev.library.dao;

import ru.otus.spring.kolychev.library.model.Book;

import java.util.List;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)

 */
public interface BookDao {
    void create(Book book);
    Book getById(String id);
    List<Book> getAll();
    void update(Book book);
    void deleteById(String id);
}
