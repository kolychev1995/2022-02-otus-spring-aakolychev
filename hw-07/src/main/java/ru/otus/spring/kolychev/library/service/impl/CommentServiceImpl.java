package ru.otus.spring.kolychev.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Transactional
    public long create(Comment comment) {
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment readById(long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public void update(long id, String comment) {
        if (commentRepository.existsById(id)) {
            commentRepository.updateContentById(id, comment);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public List<Comment> getCommentByBookId(long bookId) {
        return commentRepository.findCommentsByBookId(bookId);
    }
}
