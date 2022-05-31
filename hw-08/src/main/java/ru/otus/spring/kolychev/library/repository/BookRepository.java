package ru.otus.spring.kolychev.library.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kolychev.library.model.Book;


public interface BookRepository extends MongoRepository<Book, String> {


}
