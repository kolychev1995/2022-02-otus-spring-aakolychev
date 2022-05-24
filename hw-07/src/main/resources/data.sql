insert into `authors` values (1, 'Фредерик Брукс');
insert into `authors` values (2, 'Том Демарко');
insert into `authors` values (3, 'Таненбаум Эндрю');
insert into `authors` values (4, 'Бос Херберт');
insert into `authors` values (5, 'Уэзеролл Дэвид');

insert into `genres` values (1, 'Управление проектами');
insert into `genres` values (2, 'Менеджмент');
insert into `genres` values (3, 'Разработка ПО');
insert into `genres` values (4, 'Роман');
insert into `genres` values (5, 'ОС и сети');
insert into `genres` values (6, 'Computer Science');

insert into `books` values (1, 'Мифический человеко-месяц, или Как создаются программные системы');
insert into `books` values (2, 'Deadline. Роман об управлении проектами');
insert into `books` values (3, 'Современные операционные системы');
insert into `books` values (4, 'Компьютерные сети');

insert into `comments` values (1, 'Отличная кинга', 1);
insert into `comments` values (2, 'Увлекательно', 2);
insert into `comments` values (3, 'Замечательно и смешно', 2);
insert into `comments` values (4, 'Весьма сложно читать, но оно того стоит', 3);


insert into `books_genres` values (1, 1);
insert into `books_genres` values (1, 2);
insert into `books_genres` values (1, 3);

insert into `books_genres` values (2, 1);
insert into `books_genres` values (2, 2);
insert into `books_genres` values (2, 3);
insert into `books_genres` values (2, 4);

insert into `books_genres` values (3, 5);
insert into `books_genres` values (3, 6);

insert into `books_genres` values (4, 5);
insert into `books_genres` values (4, 6);


insert into `books_authors` values (1, 1);

insert into `books_authors` values (2, 2);

insert into `books_authors` values (3, 3);
insert into `books_authors` values (3, 4);

insert into `books_authors` values (4, 3);
insert into `books_authors` values (4, 5);
