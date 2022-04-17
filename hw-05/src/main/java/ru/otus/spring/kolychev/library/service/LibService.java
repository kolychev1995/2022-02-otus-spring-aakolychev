package ru.otus.spring.kolychev.library.service;

import ru.otus.spring.kolychev.library.model.Book;

import java.util.List;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
public interface LibService {
    String createBook(String rowBook);
    Book readById(String id);
    List<Book> getAll();
    void updateBookTitle(String id, String title);
    void deleteById(String id);
}
