package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kolychev.library.model.Comment;
import ru.otus.spring.kolychev.library.repository.CommentRepository;
import ru.otus.spring.kolychev.library.service.CommentService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public long create(Comment comment) {
        return commentRepository.save(comment).getId();
    }

    @Override
    public Comment readById(long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public void update(long id, String content) {
        Comment comment = commentRepository.findById(id).orElseThrow(NoSuchElementException::new);
        comment.setContent(content);
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Comment> getCommentByBookId(long bookId) {
        Comment comment = new Comment();
        comment.setBookId(bookId);
        return commentRepository.findAll(Example.of(comment, ExampleMatcher.matchingAll().withIgnorePaths("id")));
    }
}
