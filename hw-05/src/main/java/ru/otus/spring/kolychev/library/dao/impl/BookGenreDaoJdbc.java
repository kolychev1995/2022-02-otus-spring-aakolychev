package ru.otus.spring.kolychev.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kolychev.library.dao.BookGenreDao;
import ru.otus.spring.kolychev.library.model.BookGenreRelation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Repository
@RequiredArgsConstructor
public class BookGenreDaoJdbc implements BookGenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final RowMapper<BookGenreRelation> rowMapper =
            ((rs, rowNum) -> new BookGenreRelation(rs.getString("id"), rs.getString("bookId"),
                                                   rs.getString("genreId")));

    @Override
    public BookGenreRelation getById(String id) {
        Map<String, String> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from books_genres where id = :id", params,
                                                           rowMapper);
    }

    @Override
    public void create(List<BookGenreRelation> bookGenreRelations) {
        Map<String,String>[] batchOfParams = new HashMap[bookGenreRelations.size()];
        int count = 0;
        for (BookGenreRelation relation : bookGenreRelations) {
            Map<String, String> params = new HashMap<>();
            params.put("id", relation.getId());
            params.put("bookId", relation.getBookId());
            params.put("genreId", relation.getGenreId());
            batchOfParams[count++] = params;
        }

        namedParameterJdbcOperations.batchUpdate("insert into books_genres (id, bookId, genreId) values (:id, :bookId, " +
                                            ":genreId)", batchOfParams);
    }

    @Override
    public void deleteByBookId(String bookId) {
        Map<String, Object> params = Collections.singletonMap("bookId", bookId);
        namedParameterJdbcOperations.update("delete from books_genres where bookId = :bookId", params);
    }
    @Override
    public int countByGenreId(String id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Integer count = namedParameterJdbcOperations
                .queryForObject("select count(*) from books_genres where genreId = :id", params, Integer.class);
        return count == null? 0: count;
    }
}
