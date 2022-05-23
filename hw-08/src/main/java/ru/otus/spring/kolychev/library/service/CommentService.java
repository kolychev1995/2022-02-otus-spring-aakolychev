package ru.otus.spring.kolychev.library.service;

import ru.otus.spring.kolychev.library.model.Comment;

import java.util.List;

public interface CommentService {
    String create(Comment comment);
    Comment readById(String id);
    void update(String id, String content);
    void deleteById(String id);
    void deleteAllByBookId(String bookId);
    List<Comment> getCommentByBookId(String bookId);
}
