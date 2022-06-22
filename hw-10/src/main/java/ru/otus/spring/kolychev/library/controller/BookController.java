package ru.otus.spring.kolychev.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.kolychev.library.controller.dto.BookDto;
import ru.otus.spring.kolychev.library.service.BookService;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/v1/lib/book")
    public List<BookDto> getAllBook() {
        return bookService.getAll().stream().map(BookDto::fromDomainObject).collect(Collectors.toList());
    }

    @DeleteMapping("/v1/lib/book/{id}")
    public void deleteBookById(@PathVariable String id) {
        bookService.deleteById(id);
    }

}
