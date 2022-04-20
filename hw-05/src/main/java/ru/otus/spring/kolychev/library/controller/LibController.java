package ru.otus.spring.kolychev.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.kolychev.library.model.Author;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Genre;
import ru.otus.spring.kolychev.library.service.LibService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrei Kolychev (ulian1414@gmail.com)
 */
@ShellComponent
public class LibController {

    private final LibService libService;

    @Autowired
    public LibController(LibService libService) {
        this.libService = libService;
    }

    @ShellMethod(key = {"getBook", "gb"}, value = "Enter id of book: gb <id>")
    public String getBook(@ShellOption String id) {
        Book book = libService.readById(id);
        return bookToPrettyString(book);
    }

    @ShellMethod(key = {"getAllBook", "gab"}, value = "Return all books")
    public String getAllBook() {
        List<Book> books = libService.getAll();
        return books.stream().map(this::bookToPrettyString)
                    .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    @ShellMethod(key = {"createBook", "cb"},
            value = "Enter book in format : createBook \"title;author1,author2;genre1,genre2\"")
    public String createBook(@ShellOption String bookData) {
        return libService.createBook(bookData);
    }

    @ShellMethod(key = {"updateTitle", "ut"},
            value = "Enter id and new book title: ut i <id> t <new title>")
    public String updateTitle(@ShellOption({"id", "i"}) String id, @ShellOption({"title", "t"}) String title) {
        libService.updateBookTitle(id, title);
        return "Книга успешно обновлена";
    }


    @ShellMethod(key = {"deleteBook", "db"},
            value = "Enter book id for delete")
    public String deleteBook(@ShellOption String bookId) {
        libService.deleteById(bookId);
        return ("Книга успешно удалена");
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
