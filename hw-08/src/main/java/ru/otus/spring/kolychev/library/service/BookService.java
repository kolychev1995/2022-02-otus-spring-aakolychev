package ru.otus.spring.kolychev.library.service;

import ru.otus.spring.kolychev.library.model.Book;

import java.util.List;


public interface BookService {
    String create(Book book);
    Book readById(String id);
    List<Book> getAll();
    void updateTitle(String id, String title);
    void deleteById(String id);
}
