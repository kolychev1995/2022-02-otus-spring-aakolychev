package ru.otus.spring.kolychev.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Document(collection = "books")
public class Book {

    public Book(String title, List<String> authors, List<String> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    @Id
    private String id;
    private String title;
    private List<String> authors;
    private List<String> genres;

}
