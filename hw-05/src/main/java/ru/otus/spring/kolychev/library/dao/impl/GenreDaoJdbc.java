package ru.otus.spring.kolychev.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kolychev.library.dao.GenreDao;
import ru.otus.spring.kolychev.library.model.Genre;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)

 */
@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final RowMapper<Genre> rowMapper =
            ((rs, rowNum) -> new Genre(rs.getString("genres.id"), rs.getString("genres.title")));

    @Override
    public void create(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("title", genre.getTitle());
        namedParameterJdbcOperations.update("insert into genres (id, title) values (:id, :title)", params);
    }

    @Override
    public Genre getByTitle(String title) {
        Map<String, String> params = Collections.singletonMap("title", title);
        return namedParameterJdbcOperations.queryForObject("select * from genres where title = :title", params,
                                                           rowMapper);
    }

    @Override
    public Genre getById(String id) {
        Map<String, String> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from genres where id = :id", params,
                                                           rowMapper);
    }

    @Override
    public void deleteById(String id) {
        Map<String, String> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from genres where id = :id", params);
    }

    @Override
    public int countByTitle(String title) {
        Map<String, String> params = Collections.singletonMap("title", title);
        Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from genres where title = :title", params,
                                                           Integer.class);
        return count == null? 0: count;
    }
}
