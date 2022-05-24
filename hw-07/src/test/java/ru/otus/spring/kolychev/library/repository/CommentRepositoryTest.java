package ru.otus.spring.kolychev.library.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.kolychev.library.model.Comment;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

class CommentRepositoryTest {

    public static final long EXISTING_ID = 1L;
    public static final String NEW_TEST_CONTENT = "New content";
    @Autowired
    CommentRepository commentRepository;

    @Test
    
    void updateContentById() {
        commentRepository.updateContentById(EXISTING_ID, NEW_TEST_CONTENT);
        Comment comment = commentRepository.getById(EXISTING_ID);
        Assertions.assertThat(comment.getContent()).isEqualTo(NEW_TEST_CONTENT);
    }
}