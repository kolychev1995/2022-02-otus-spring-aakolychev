package ru.otus.spring.kolychev.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kolychev.library.dao.BookAuthorDao;
import ru.otus.spring.kolychev.library.model.BookAuthorRelation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Repository
@RequiredArgsConstructor
public class BookAuthorDaoJdbc implements BookAuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void create(List<BookAuthorRelation> relations) {
        Map<String, String>[] batchOfParams = new HashMap[relations.size()];
        int count = 0;
        for (BookAuthorRelation relation : relations) {
            Map<String, String> params = new HashMap<>();
            params.put("id", relation.getId());
            params.put("bookId", relation.getBookId());
            params.put("genreId", relation.getAuthorId());
            batchOfParams[count++] = params;
        }

        namedParameterJdbcOperations.batchUpdate("insert into books_authors (id, bookId, authorId) " +
                                                 "values (:id, :bookId, :genreId)", batchOfParams);
    }

    @Override
    public BookAuthorRelation getById(String id) {
        Map<String, String> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from books_authors where id = :id", params,
                                                    (rs, rowNum) -> new BookAuthorRelation(rs.getString("id"),
                                                                                           rs.getString("bookId"),
                                                                                           rs.getString("authorId")));
    }

    @Override
    public void deleteByBookId(String bookId) {
        Map<String, Object> params = Collections.singletonMap("bookId", bookId);
        namedParameterJdbcOperations.update("delete from books_authors where bookId = :bookId", params);
    }

    @Override
    public int countByAuthorId(String id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Integer count = namedParameterJdbcOperations
                .queryForObject("select count(*) from books_authors where authorId = :id", params, Integer.class);
        return count == null ? 0 : count;
    }
}
