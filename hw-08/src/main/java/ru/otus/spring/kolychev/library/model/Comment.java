package ru.otus.spring.kolychev.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "comments")
public class Comment {

    public Comment(String content, String bookId) {
        this.content = content;
        this.bookId = bookId;
    }

    @Id
    private String id;
    private String content;
    private String bookId;
}
