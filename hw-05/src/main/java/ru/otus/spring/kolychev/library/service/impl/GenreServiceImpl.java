package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kolychev.library.dao.BookGenreDao;
import ru.otus.spring.kolychev.library.dao.GenreDao;
import ru.otus.spring.kolychev.library.model.Genre;
import ru.otus.spring.kolychev.library.service.GenreService;

import java.util.UUID;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;
    private final BookGenreDao bookGenreDao;

    @Override
    public String create(String title) {
        if (!isExists(title)) {
            String id = UUID.randomUUID().toString();
            genreDao.create(new Genre(id, title));
            return id;
        } else {
            return genreDao.getByTitle(title).getId();
        }
    }

    @Override
    public void deleteById(String id) {
        if(bookGenreDao.countByGenreId(id) == 0) {
            genreDao.deleteById(id);
        }
    }

    @Override
    public boolean isExists(String title) {
        return genreDao.countByTitle(title) > 0;
    }

}
