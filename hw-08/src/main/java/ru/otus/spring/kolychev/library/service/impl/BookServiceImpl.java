package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.service.BookService;
import ru.otus.spring.kolychev.library.service.CommentService;

import java.util.List;
import java.util.NoSuchElementException;


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
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void updateTitle(String id, String title) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(title);
        bookRepository.save(book);
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
