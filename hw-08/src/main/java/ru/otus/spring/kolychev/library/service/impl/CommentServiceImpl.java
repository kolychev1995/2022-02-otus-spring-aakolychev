package ru.otus.spring.kolychev.library.service.impl;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kolychev.library.model.Comment;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.repository.CommentRepository;
import ru.otus.spring.kolychev.library.service.CommentService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public String create(Comment comment) {
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment readById(String id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void update(String id, String content) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setContent(content);
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        try {
            commentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public void deleteAllByBookId(String bookId) {
        try {
            List<Comment> comments = getCommentByBookId(bookId);
            commentRepository.deleteAll(comments);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Comment> getCommentByBookId(String bookId) {
        Comment comment = new Comment();
        comment.setBookId(bookId);
        return commentRepository.findAll(Example.of(comment, ExampleMatcher.matchingAll().withIgnorePaths("id")));
    }
}
