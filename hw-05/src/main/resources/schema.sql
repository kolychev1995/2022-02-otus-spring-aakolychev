DROP TABLE IF EXISTS books_authors;
DROP TABLE IF EXISTS books_genres;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS authors;

CREATE TABLE genres
(
    id    uuid,
    title varchar(255),

    primary key (id)

);

CREATE TABLE authors
(
    id   uuid,
    name varchar(255),

    primary key (id)
);

CREATE TABLE books
(
    id    uuid,
    title varchar(255),

    primary key (id)
);

CREATE TABLE books_genres
(
    id      uuid,
    bookId  uuid,
    genreId uuid,

    primary key (id)
--     foreign key (genreId) references genres (id),
--     foreign key (bookId) references books (id)
);

CREATE TABLE books_authors
(
    id       uuid,
    bookId   uuid,
    authorId uuid,

    primary key (id)
--     foreign key (authorId) references authors (id),
--     foreign key (bookId) references books (id)
);