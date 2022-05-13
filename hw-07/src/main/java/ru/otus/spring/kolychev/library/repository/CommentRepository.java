package ru.otus.spring.kolychev.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.kolychev.library.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
