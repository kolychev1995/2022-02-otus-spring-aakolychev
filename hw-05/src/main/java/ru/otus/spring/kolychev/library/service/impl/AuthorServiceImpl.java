package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kolychev.library.dao.AuthorDao;
import ru.otus.spring.kolychev.library.dao.BookAuthorDao;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.service.AuthorService;

import java.util.UUID;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final BookAuthorDao bookAuthorDao;

    @Override
    public String create(String name) {
        if (!isExists(name)) {
            String id = UUID.randomUUID().toString();
            authorDao.create(new Author(id, name));
            return id;
        } else {
            return authorDao.getByName(name).getId();
        }
    }

    @Override
    public void deleteById(String id) {
        if(bookAuthorDao.countByAuthorId(id) == 0) {
            authorDao.deleteById(id);
        }
    }

    @Override
    public boolean isExists(String name) {
        return authorDao.countByName(name) > 0;
    }
}
