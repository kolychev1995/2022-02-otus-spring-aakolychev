insert into `authors` values ('92cfd2bd-e57d-495a-8a28-b69157420cc2', 'Фредерик Брукс');
insert into `authors` values ('6c4b138e-5ba6-4e29-879e-07597159f712', 'Том Демарко');
insert into `authors` values ('5bca5bfd-53ae-4ee4-b251-1f92fb9b78ad', 'Таненбаум Эндрю');
insert into `authors` values ('61227031-fe8c-4aef-b47b-11d9cc4bcae4', 'Бос Херберт');
insert into `authors` values ('e5735994-b40f-4464-ac75-6572628fa140', 'Уэзеролл Дэвид');

insert into `genres` values ('eb83b4c6-f80d-49af-ad5b-eda688f78245', 'Управление проектами');
insert into `genres` values ('2609013f-573d-4b83-b96e-5f6fd1150de5', 'Менеджмент');
insert into `genres` values ('6f9849c2-002c-4ca8-b458-5bb87ff06607', 'Разработка ПО');
insert into `genres` values ('fc3d607a-8289-4a2c-9b31-cfcc97a31ae1', 'Роман');
insert into `genres` values ('49f4c4f3-420d-4963-ac99-6714e8aa74be', 'ОС и сети');
insert into `genres` values ('bd5c2f76-f969-48aa-a396-71fc9e69e147', 'Computer Science');

insert into `books` values ('2f4e4efd-1b4d-4c44-a091-af4047d60582', 'Мифический человеко-месяц, или Как создаются программные системы');
insert into `books` values ('0efe9df2-31c1-47c8-9c74-8838828d900b', 'Deadline. Роман об управлении проектами');
insert into `books` values ('6976692b-0e40-497a-a984-d863c3468ee9', 'Современные операционные системы');
insert into `books` values ('f5c2533e-605c-4d7b-b38f-1115af2c9a42', 'Компьютерные сети');


insert into `books_genres` values ('01e221db-ccf1-4724-a49d-694f2d3c0198', '2f4e4efd-1b4d-4c44-a091-af4047d60582', 'eb83b4c6-f80d-49af-ad5b-eda688f78245');
insert into `books_genres` values ('d6a6c0a4-9c2f-4dae-aa78-64e410154ab9', '2f4e4efd-1b4d-4c44-a091-af4047d60582', '2609013f-573d-4b83-b96e-5f6fd1150de5');
insert into `books_genres` values ('81061a2b-c75e-429e-8b22-fbe82713f78a', '2f4e4efd-1b4d-4c44-a091-af4047d60582', '6f9849c2-002c-4ca8-b458-5bb87ff06607');

insert into `books_genres` values ('f2a1782c-1bb1-4a8d-ab86-4fe5408c649b', '0efe9df2-31c1-47c8-9c74-8838828d900b', 'eb83b4c6-f80d-49af-ad5b-eda688f78245');
insert into `books_genres` values ('c2daf2e8-f05f-4b8d-bc6e-5935f7386c65', '0efe9df2-31c1-47c8-9c74-8838828d900b', '2609013f-573d-4b83-b96e-5f6fd1150de5');
insert into `books_genres` values ('74669668-2c51-4dd4-9e82-066f35603334', '0efe9df2-31c1-47c8-9c74-8838828d900b', '6f9849c2-002c-4ca8-b458-5bb87ff06607');
insert into `books_genres` values ('9a43fbe7-81bc-4c49-a3a9-abdc5fb66c21', '0efe9df2-31c1-47c8-9c74-8838828d900b', 'fc3d607a-8289-4a2c-9b31-cfcc97a31ae1');

insert into `books_genres` values ('e08e2a87-4b06-4558-8aa7-c7093d3e4e2d', '6976692b-0e40-497a-a984-d863c3468ee9', '49f4c4f3-420d-4963-ac99-6714e8aa74be');
insert into `books_genres` values ('5fe200d3-c6e7-4ba5-af1e-c386592b4f7f', '6976692b-0e40-497a-a984-d863c3468ee9', 'bd5c2f76-f969-48aa-a396-71fc9e69e147');

insert into `books_genres` values ('29780a01-2fdb-4478-821b-0758f0ebec4f', 'f5c2533e-605c-4d7b-b38f-1115af2c9a42', '49f4c4f3-420d-4963-ac99-6714e8aa74be');
insert into `books_genres` values ('1486a1c5-508a-45a7-9eb7-4541116eb7ed', 'f5c2533e-605c-4d7b-b38f-1115af2c9a42', 'bd5c2f76-f969-48aa-a396-71fc9e69e147');


insert into `books_authors` values ('f395cd09-ad00-46c1-a040-c450ecf5a5d2', '2f4e4efd-1b4d-4c44-a091-af4047d60582', '92cfd2bd-e57d-495a-8a28-b69157420cc2');

insert into `books_authors` values ('fd5bcff1-5c1c-4db3-b9c1-a7611814a15d', '0efe9df2-31c1-47c8-9c74-8838828d900b', '6c4b138e-5ba6-4e29-879e-07597159f712');

insert into `books_authors` values ('25793836-3a2a-48c0-8b1b-aa229867e7ce', '6976692b-0e40-497a-a984-d863c3468ee9', '5bca5bfd-53ae-4ee4-b251-1f92fb9b78ad');
insert into `books_authors` values ('1d4a4fb5-4696-4af2-95ce-a5a4348f50fa', '6976692b-0e40-497a-a984-d863c3468ee9', '61227031-fe8c-4aef-b47b-11d9cc4bcae4');

insert into `books_authors` values ('a23fddde-3153-4af3-bf86-bd62878341fb', 'f5c2533e-605c-4d7b-b38f-1115af2c9a42', '5bca5bfd-53ae-4ee4-b251-1f92fb9b78ad');
insert into `books_authors` values ('b37c80fa-f0ff-41bc-afd6-ba09d56b9e24', 'f5c2533e-605c-4d7b-b38f-1115af2c9a42', 'e5735994-b40f-4464-ac75-6572628fa140');
