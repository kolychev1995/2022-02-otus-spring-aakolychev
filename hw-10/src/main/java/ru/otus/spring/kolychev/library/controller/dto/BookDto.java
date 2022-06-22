package ru.otus.spring.kolychev.library.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.kolychev.library.model.Book;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private String id;
    @NotBlank(message = "Title field should not be blank")
    private String title;
    @NotBlank(message = "Authors field should not be blank")
    private String authors;
    @NotBlank(message = "Genres field should not be blank")
    private String genres;

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(book.getId(),
                           book.getTitle(),
                           book.getAuthors().stream().collect(Collectors.joining(", ")),
                           book.getGenres().stream().collect(Collectors.joining(", ")));
    }

    public Book toDomainObject() {
        return new Book(id,
                        title.toUpperCase(),
                        Arrays.stream(authors.split(", ")).map(String::toUpperCase).collect(Collectors.toList()),
                        Arrays.stream(genres.split(", ")).map(String::toUpperCase).collect(Collectors.toList()));
    }
}
