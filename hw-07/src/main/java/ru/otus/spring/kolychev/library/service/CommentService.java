package ru.otus.spring.kolychev.library.service;

import ru.otus.spring.kolychev.library.model.Comment;

import java.util.List;

public interface CommentService {
    long create(Comment comment);
    Comment readById(long id);
    List<Comment> getAll();
    void update(long id, String comment);
    void deleteById(long id);
    List<Comment> getCommentByBookId(long bookId);
}
