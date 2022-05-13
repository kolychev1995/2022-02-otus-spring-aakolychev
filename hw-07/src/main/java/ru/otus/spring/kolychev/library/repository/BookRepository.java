package ru.otus.spring.kolychev.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.kolychev.library.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {
}
