package ru.otus.spring.kolychev.library.model;

import lombok.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class BookAuthorRelation {
    private final String id;
    private final String bookId;
    private final String authorId;
}
