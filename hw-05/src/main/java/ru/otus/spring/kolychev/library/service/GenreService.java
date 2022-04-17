package ru.otus.spring.kolychev.library.service;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
public interface GenreService {
    String create(String title);
    void deleteById(String id);
    boolean isExists(String title);
}
