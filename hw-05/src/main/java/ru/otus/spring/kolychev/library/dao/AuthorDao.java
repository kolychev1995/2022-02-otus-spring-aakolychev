package ru.otus.spring.kolychev.library.dao;

import ru.otus.spring.kolychev.library.model.Author;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)

 */
public interface AuthorDao {
    void create(Author author);
    Author getByName(String name);
    Author getById(String id);
    void deleteById(String id);
    int countByName(String name);
}
