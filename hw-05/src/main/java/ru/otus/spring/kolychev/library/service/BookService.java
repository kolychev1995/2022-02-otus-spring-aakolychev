package ru.otus.spring.kolychev.library.service;

import ru.otus.spring.kolychev.library.model.Book;

import java.util.List;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
public interface BookService {
    void create(Book book);
    Book readById(String id);
    List<Book> getAll();
    void updateTitle(String id, String title);
    void deleteById(String id);
}
