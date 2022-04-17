package ru.otus.spring.kolychev.library.service;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
public interface AuthorService {
    String create(String name);
    void deleteById(String id);
    boolean isExists(String name);
}
