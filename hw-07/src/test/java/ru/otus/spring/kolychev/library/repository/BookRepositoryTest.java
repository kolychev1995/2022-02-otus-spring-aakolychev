package ru.otus.spring.kolychev.library.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kolychev.library.model.Book;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

class BookRepositoryTest {

    public static final long EXISTING_ID = 1L;
    public static final String NEW_TITLE = "new title";
    @Autowired
    BookRepository bookRepository;

    @Test
    void shouldSuccessUpdateTitleById() {
        bookRepository.updateTitleById(EXISTING_ID, NEW_TITLE);
        Book book = bookRepository.getById(EXISTING_ID);
        Assertions.assertThat(book.getTitle()).isEqualTo(NEW_TITLE);
    }
}