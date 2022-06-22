package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.service.BookService;
import ru.otus.spring.kolychev.library.service.CommentService;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentService commentService;

    @Override
    public String create(Book book) {
        return bookRepository.save(book).getId();
    }

    @Override
    public Book readById(String id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<String> getAllAuthors() {
        return bookRepository.findAll()
                             .stream()
                             .map(Book::getAuthors)
                             .flatMap(Collection::stream)
                             .distinct()
                             .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        Book exBook = new Book();
        exBook.setAuthors(List.of(author.toUpperCase(Locale.ROOT)));
        final ExampleMatcher matcher = ExampleMatcher.matching()
                                                     .withIgnoreNullValues()
                                                     .withMatcher("authors",
                                                                  match -> match.transform(optList -> optList.map(list -> String.join("|", (List<String>) list)
                                                                  )).contains());

        return bookRepository.findAll(Example.of(exBook, matcher));
    }

    @Override
    public void update(Book book) {
        if (bookRepository.existsById(book.getId())) {
            bookRepository.save(book);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            bookRepository.deleteById(id);
            commentService.deleteAllByBookId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }
}
