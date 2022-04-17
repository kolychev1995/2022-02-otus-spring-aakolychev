package ru.otus.spring.kolychev.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kolychev.library.dao.BookDao;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final RowMapper<Book> rowMapper = ((rs, rowNum) -> {
        Set<Author> authors = new HashSet<>();
        Set<Genre> genres = new HashSet<>();
        String bookTitle = rs.getString("BOOKS.TITLE");
        String bookId = rs.getString("BOOKS.ID");
        do {
            String authorId = rs.getString("AUTHORS.ID");
            if (authorId != null) {
                authors.add(new Author(authorId, rs.getString("AUTHORS.NAME")));
            }
            String genreId = rs.getString("GENRES.ID");
            if (genreId != null) {
                genres.add(new Genre(genreId, rs.getString("GENRES.TITLE")));
            }
        } while (rs.next());
        return new Book(bookId, bookTitle, authors, genres);
    });

    @Override
    public void create(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());

        namedParameterJdbcOperations.update("insert into books (id, title) values (:id, :title)", params);
    }

    @Override
    public Book getById(String id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        return namedParameterJdbcOperations.queryForObject("SELECT BOOKS.ID, BOOKS.TITLE, AUTHORS.ID, AUTHORS.NAME, " +
                                                           "GENRES.ID, GENRES.TITLE\n" +
                                                           "FROM BOOKS \n" +
                                                           "left join BOOKS_AUTHORS on BOOKS.ID = BOOKS_AUTHORS" +
                                                           ".BOOKID\n" +
                                                           "left join AUTHORS on BOOKS_AUTHORS.AUTHORID = AUTHORS.ID\n" +
                                                           "left join BOOKS_GENRES on BOOKS.ID = BOOKS_GENRES" +
                                                           ".BOOKID\n" +
                                                           "left join GENRES on BOOKS_GENRES.GENREID = GENRES.id\n" +
                                                           "where BOOKS.id= :id",
                                                           params, rowMapper
        );

    }

    @Override
    public List<Book> getAll() {
        BookRowCallBackHandler backHandler = new BookRowCallBackHandler();
        namedParameterJdbcOperations.query(
                "SELECT BOOKS.ID, BOOKS.TITLE, AUTHORS.ID, AUTHORS.NAME, GENRES.ID, GENRES.TITLE  FROM AUTHORS \n" +
                "inner join BOOKS_AUTHORS on AUTHORS .id = authorId\n" +
                "inner join BOOKS on BOOKS_AUTHORS.bookId  = BOOKS.id\n" +
                "inner join BOOKS_GENRES on BOOKS_GENRES.BOOKID = BOOKS.id\n" +
                "inner join GENRES on GENRES.ID = BOOKS_GENRES.GENREID\n", backHandler);
        return backHandler.getBookList();
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());

        namedParameterJdbcOperations.update("update books set title = :title where id = :id", params);
    }

    @Override
    public void deleteById(String id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books where id = :id", params);
    }

    private static class BookRowCallBackHandler implements RowCallbackHandler {

        Map<String, Book> books = new HashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            String id = rs.getString("BOOKS.ID");
            if (!books.containsKey(id)) {
                books.put(id, new Book(id, rs.getString("BOOKS.TITLE"), new HashSet<>(), new HashSet<>()));
            }
            books.get(id).getAuthors().add(new Author(rs.getString("AUTHORS.ID"), rs.getString("AUTHORS.NAME")));
            books.get(id).getGenres().add(new Genre(rs.getString("GENRES.ID"), rs.getString("GENRES.TITLE")));
        }

        List<Book> getBookList() {
            return new ArrayList<>(books.values());
        }
    }
}
