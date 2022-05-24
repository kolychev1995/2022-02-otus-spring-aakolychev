package ru.otus.spring.kolychev.library.service;

import ru.otus.spring.kolychev.library.model.Book;

import java.util.List;


public interface BookService {
    long create(Book book);
    Book readById(long id);
    List<Book> getAll();
    void updateTitle(long id, String title);
    void deleteById(long id);
}
