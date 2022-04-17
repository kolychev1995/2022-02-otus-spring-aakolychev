package ru.otus.spring.kolychev.library.dao;

import ru.otus.spring.kolychev.library.model.Genre;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)

 */
public interface GenreDao {
    void create(Genre genre);
    Genre getByTitle(String title);
    Genre getById(String id);
    void deleteById(String id);
    int countByTitle(String title);
}
