package ru.otus.spring.kolychev.library.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.kolychev.library.model.Comment;
import ru.otus.spring.kolychev.library.service.CommentService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ShellComponent
public class CommentController {

    public static final String WRONG_ID_FORMAT_MESSAGE = "Идентификатор комментария должен состоять только из цифр";
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ShellMethod(key = {"getComment", "gc"}, value = "Enter id of comment: getComment <id>")
    public String getComment(@ShellOption String id) {
        try {
            Comment comment = commentService.readById(id);
            return commentToPrettyString(comment);
        } catch (NoSuchElementException nsee) {
            return "Комментарий не найден";
        }
    }

    @ShellMethod(key = {"getBookComments", "gbc"}, value = "Return all comments for book: getBookComments i <bookId>")
    public String getBookComments(@ShellOption({"id", "i"}) String bookId) {
        List<Comment> comments = commentService.getCommentByBookId(bookId);
        return comments.stream().map(this::commentToPrettyString)
                       .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    @ShellMethod(key = {"createComment", "cc"},
            value = "Enter id book and comment content : createComment i <bookId> c <content>")
    public String createComment(@ShellOption({"id", "i"}) String bookId,
                                @ShellOption({"content", "c"}) String content) {
        Comment comment;
        try {
            comment = new Comment( content, bookId);
        } catch (NumberFormatException nfe) {
            return WRONG_ID_FORMAT_MESSAGE;
        }
        return String.valueOf(commentService.create(comment));
    }

    @ShellMethod(key = {"updateComment", "uc"},
            value = "Enter id and new comment content: uc i <id> c <new content>")
    public String updateTitle(@ShellOption({"id", "i"}) String id, @ShellOption({"content", "c"}) String content) {
        try {
            commentService.update(id, content);
        } catch (NoSuchElementException nsee) {
            return "Книга не найдена, обновление невозможно";
        }
        return "Комментарий успешно обновлен";
    }

    @ShellMethod(key = {"deleteComment", "dc"}, value = "Enter comment id for delete")
    public String deleteComment(@ShellOption String commentId) {
        try {
            commentService.deleteById(commentId);
        } catch (NoSuchElementException nsee) {
            return ("Книга не найдена, удаление невозможно");
        }
        return ("Комментарий успешно удален");
    }

    private String commentToPrettyString(Comment comment) {
        StringBuilder sb = new StringBuilder();
        sb.append("Идентификатор: ")
          .append(comment.getId())
          .append(System.lineSeparator())
          .append("Идентификатор книги: ")
          .append(comment.getBookId())
          .append(System.lineSeparator())
          .append("Содержание: ")
          .append(comment.getContent());
        return sb.toString();
    }

}
