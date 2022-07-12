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
        Book book = bookRepository.save(new Book("МИФИЧЕСКИЙ ЧЕЛОВЕКО-МЕСЯЦ, ИЛИ КАК СОЗДАЮТСЯ ПРОГРАММНЫЕ СИСТЕМЫ",
                                                 List.of("ФРЕДЕРИК БРУКС"),
                                                 List.of("УПРАВЛЕНИЕ ПРОЕКТАМИ", "МЕНЕДЖМЕНТ", "РАЗРАБОТКА ПО")));
        commentRepository.save(new Comment("Отличная книга", book.getId()));

        Book book2 = bookRepository.save(new Book("DEADLINE. РОМАН ОБ УПРАВЛЕНИИ ПРОЕКТАМИ",
                                                 List.of("ТОМ ДЕМАРКО"),
                                                 List.of("УПРАВЛЕНИЕ ПРОЕКТАМИ", "МЕНЕДЖМЕНТ", "РАЗРАБОТКА ПО", "РОМАН")));
        commentRepository.save(new Comment("Увлекательно", book2.getId()));
        commentRepository.save(new Comment("Замечательно и смешно", book2.getId()));

        bookRepository.save(new Book("СОВРЕМЕННЫЕ ОПЕРАЦИОННЫЕ СИСТЕМЫ",
                                                  List.of("ТАНЕНБАУМ ЭНДРЮ", "БОС ХЕРБЕРТ"),
                                                  List.of("ОС И СЕТИ", "COMPUTER SCIENCE")));
    }
}
