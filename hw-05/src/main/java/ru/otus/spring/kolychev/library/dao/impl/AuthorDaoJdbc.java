package ru.otus.spring.kolychev.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kolychev.library.dao.AuthorDao;
import ru.otus.spring.kolychev.library.model.Author;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)

 */
@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final RowMapper<Author> rowMapper =
            ((rs, rowNum) -> new Author(rs.getString("authors.id"), rs.getString("authors.name")));

    @Override
    public void create(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        namedParameterJdbcOperations.update("insert into authors (id, name) values (:id, :name)", params);
    }

    @Override
    public Author getByName(String name) {
        Map<String, String> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.queryForObject("select * from authors where name = :name", params,
                                                       rowMapper);
    }

    @Override
    public Author getById(String id) {
        Map<String, String> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from authors where id = :id", params,
                                                           rowMapper);
    }

    @Override
    public void deleteById(String id) {
        Map<String, String> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from authors where id = :id", params);
    }

    @Override
    public int countByName(String name) {
        Map<String, String> params = Collections.singletonMap("name", name);
        Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from authors where name = :name",
                                                                    params,
                                                                    Integer.class);
        return count == null? 0: count;
    }
}
