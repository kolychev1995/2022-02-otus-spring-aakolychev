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
    @Transactional
    public long create(Book book) {
        return bookRepository.save(book).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Book readById(long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void updateTitle(long id, String title) {
        if (bookRepository.existsById(id)) {
            bookRepository.updateTitleById(id, title);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }
}
