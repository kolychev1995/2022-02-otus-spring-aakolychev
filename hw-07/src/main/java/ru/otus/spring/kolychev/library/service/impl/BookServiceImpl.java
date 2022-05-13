package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public long create(Book book) {
        return bookRepository.save(book).getId();
    }

    @Override
    public Book readById(long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void updateTitle(long id, String title) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(title);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }
}
