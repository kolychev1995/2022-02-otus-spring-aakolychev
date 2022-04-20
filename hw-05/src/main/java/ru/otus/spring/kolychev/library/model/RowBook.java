package ru.otus.spring.kolychev.library.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@RequiredArgsConstructor
@Getter
@Setter
public class RowBook {

    private final String title;
    private final List<String> authors;
    private final List<String> genres;
}
