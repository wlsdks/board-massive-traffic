create table if not exists board.category
(
    id   int auto_increment
        primary key,
    name varchar(45) not null
);

create table if not exists board.tag
(
    id   int auto_increment
        primary key,
    name varchar(45)  not null,
    url  varchar(255) null
);

create table if not exists board.user
(
    id         int auto_increment
        primary key,
    userId     varchar(45)  not null,
    password   varchar(255) not null,
    nickname   varchar(45)  not null,
    isAdmin    tinyint      not null,
    createTime datetime     not null,
    isWithDraw tinyint      not null,
    status     varchar(45)  null
);

create table if not exists board.post
(
    id         int auto_increment
        primary key,
    name       varchar(45)  not null,
    isAdmin    tinyint      not null,
    contents   varchar(450) not null,
    createTime datetime     not null,
    views      int          not null,
    userId     int          not null,
    fileId     int          not null,
    categoryId int          not null,
    updateTime datetime     not null,
    constraint FK_1
        foreign key (userId) references board.user (id),
    constraint FK_2
        foreign key (categoryId) references board.category (id)
);

create table if not exists board.comment
(
    id           int auto_increment
        primary key,
    postId       int          not null,
    contents     varchar(300) not null,
    subCommentId int          not null,
    constraint FK_4
        foreign key (postId) references board.post (id),
    constraint FK_5
        foreign key (subCommentId) references board.comment (id)
);

create index FK_1
    on board.comment (postId);

create index FK_2
    on board.comment (subCommentId);

create table if not exists board.file
(
    id        int auto_increment
        primary key,
    path      varchar(45) not null,
    name      varchar(45) not null,
    extension varchar(45) not null,
    postId    int         not null,
    constraint FK_3
        foreign key (postId) references board.post (id)
);

create index FK_1
    on board.file (postId);

create table if not exists board.postTag
(
    id     int auto_increment
        primary key,
    postId int not null,
    tagId  int not null,
    constraint FK_6
        foreign key (postId) references board.post (id),
    constraint FK_7
        foreign key (tagId) references board.tag (id)
);

create index FK_1
    on board.postTag (postId);

create index FK_2
    on board.postTag (tagId);

