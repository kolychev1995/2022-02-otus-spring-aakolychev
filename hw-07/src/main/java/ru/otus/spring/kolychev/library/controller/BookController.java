package ru.otus.spring.kolychev.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Genre;
import ru.otus.spring.kolychev.library.service.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@ShellComponent
public class BookController {

    public static final String WRONG_ID_FORMAT_MESSAGE = "Идентификатор книги должен состоять только из цифр";
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = {"getBook", "gb"}, value = "Enter id of book: gb <id>")
    public String getBook(@ShellOption String id) {
        try {
            Book book = bookService.readById(Long.parseLong(id));
            return bookToPrettyString(book);
        } catch (NoSuchElementException nsee) {
            return "Книга не найдена";
        } catch (NumberFormatException nfe) {
            return WRONG_ID_FORMAT_MESSAGE;
        }

    }

    @ShellMethod(key = {"getAllBook", "gab"}, value = "Return all books")
    public String getAllBook() {
        List<Book> books = bookService.getAll();
        return books.stream().map(this::bookToPrettyString)
                    .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    @ShellMethod(key = {"createBook", "cb"},
            value = "Enter book in format : createBook \"title;author1,author2;genre1,genre2\"")
    public String createBook(@ShellOption String bookData) {
        Book book;
        try {
            book = parseBook(bookData);
        } catch (Exception e) {
            return "Ошибка при разборе данных книги. " +
                   "Данные книги должны быть в формате \"title;author1,author2;genre1,genre2\"";
        }
        return String.valueOf(bookService.create(book));
    }

    @ShellMethod(key = {"updateTitle", "ut"},
            value = "Enter id and new book title: ut i <id> t <new title>")
    public String updateTitle(@ShellOption({"id", "i"}) String id, @ShellOption({"title", "t"}) String title) {
        try {
            bookService.updateTitle(Long.parseLong(id), title);
        } catch (NoSuchElementException nsee) {
            return "Книга не найдена, обновление невозможно";
        } catch (NumberFormatException nfe) {
            return WRONG_ID_FORMAT_MESSAGE;
        }
        return "Книга успешно обновлена";
    }


    @ShellMethod(key = {"deleteBook", "db"},
            value = "Enter book id for delete")
    public String deleteBook(@ShellOption String bookId) {
        try {
            bookService.deleteById(Long.parseLong(bookId));
        } catch (NoSuchElementException nsee) {
            return ("Книга не найдена, удаление невозможно");
        } catch (NumberFormatException nfe) {
            return WRONG_ID_FORMAT_MESSAGE;
        }
        return ("Книга успешно удалена");
    }

    private Book parseBook(String rowBookData) {
        String[] data = rowBookData.replace("\"", "")
                                   .split(";");
        List<Author> authors = Arrays.stream(data[1].split(","))
                                     .map(e -> new Author(0L, e))
                                     .collect(Collectors.toList());
        List<Genre> genres = Arrays.stream(data[2].split(","))
                                   .map(e -> new Genre(0L, e))
                                   .collect(Collectors.toList());

        return new Book(0L, data[0], authors, genres, null);
    }

    private String bookToPrettyString(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(book.getTitle())
          .append(System.lineSeparator())
          .append("Идентификатор: ")
          .append(book.getId())
          .append(System.lineSeparator())
          .append("Авторы: ")
          .append(book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")))
          .append(System.lineSeparator())
          .append("Жанры: ")
          .append(book.getGenres().stream().map(Genre::getTitle).collect(Collectors.joining(", ")));
        return sb.toString();
    }
}
