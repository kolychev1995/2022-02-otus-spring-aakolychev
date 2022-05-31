package ru.otus.spring.kolychev.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.service.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@ShellComponent
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = {"getBook", "gb"}, value = "Enter id of book: gb <id>")
    public String getBook(@ShellOption String id) {
        try {
            Book book = bookService.readById(id);
            return bookToPrettyString(book);
        } catch (NoSuchElementException nsee) {
            return "Книга не найдена";
        }
    }

    @ShellMethod(key = {"getBookByAuthor", "gba"}, value = "Enter author of book: gba <author>")
    public String getBookByAuthor(@ShellOption String author) {
       List<Book> books = bookService.getBookByAuthor(author);
        return books.stream()
                    .map(this::bookToPrettyString)
                    .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    @ShellMethod(key = {"getAllAuthors", "gaa"}, value = "Return all authors")
    public String getAllAuthors() {
        List<String> authors = bookService.getAllAuthors();
        return String.join(System.lineSeparator(), authors);
    }



    @ShellMethod(key = {"getAllBook", "gab"}, value = "Return all books")
    public String getAllBook() {
        List<Book> books = bookService.getAll();
        return books.stream()
                    .map(this::bookToPrettyString)
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
            bookService.updateTitle(id, title);
        } catch (NoSuchElementException nsee) {
            return "Книга не найдена, обновление невозможно";
        }
        return "Книга успешно обновлена";
    }


    @ShellMethod(key = {"deleteBook", "db"},
            value = "Enter book id for delete")
    public String deleteBook(@ShellOption String bookId) {
        try {
            bookService.deleteById(bookId);
        } catch (NoSuchElementException nsee) {
            return ("Книга не найдена, удаление невозможно");
        }
        return ("Книга успешно удалена");
    }

    private Book parseBook(String rowBookData) {
        String[] data = rowBookData.toUpperCase(Locale.ROOT)
                                   .replace("\"", "")
                                   .split(";");
        List<String> authors = Arrays.stream(data[1].split(","))
                                     .collect(Collectors.toList());
        List<String> genres = Arrays.stream(data[2].split(","))
                                   .collect(Collectors.toList());

        return new Book(data[0], authors, genres);
    }

    private String bookToPrettyString(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(book.getTitle())
          .append(System.lineSeparator())
          .append("Идентификатор: ")
          .append(book.getId())
          .append(System.lineSeparator())
          .append("Авторы: ")
          .append(book.getAuthors().stream().collect(Collectors.joining(", ")))
          .append(System.lineSeparator())
          .append("Жанры: ")
          .append(book.getGenres().stream().collect(Collectors.joining(", ")));
        return sb.toString();
    }
}
