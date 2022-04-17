package ru.otus.spring.kolychev.library.model;

import lombok.*;

import java.util.Set;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)

 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Book {
    private final String id;
    private final String title;
    private final Set<Author> authors;
    private final Set<Genre> genres;
}
