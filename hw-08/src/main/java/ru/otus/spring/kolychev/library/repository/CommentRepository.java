package ru.otus.spring.kolychev.library.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kolychev.library.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
