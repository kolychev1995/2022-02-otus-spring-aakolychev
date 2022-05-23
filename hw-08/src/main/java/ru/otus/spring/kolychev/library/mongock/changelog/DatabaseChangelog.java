package ru.otus.spring.kolychev.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.model.Comment;
import ru.otus.spring.kolychev.library.repository.BookRepository;
import ru.otus.spring.kolychev.library.repository.CommentRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "kolychev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBook", author = "kolychev")
    public void insertBooks(BookRepository bookRepository, CommentRepository commentRepository) {
        Book book = bookRepository.save(new Book("Мифический человеко-месяц, или Как создаются программные системы",
                                                 List.of("Фредерик Брукс"),
                                                 List.of("Управление проектами", "Менеджмент", "Разработка ПО")));
        commentRepository.save(new Comment("Отличная книга", book.getId()));
    }
}
