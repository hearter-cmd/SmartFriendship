create table if not exists user_beauty
(
    id               bigint        null,
    userAccount      varchar(256)  null,
    userPassword     varchar(512)  null,
    email            varchar(50)   null,
    sex              tinyint       null,
    person_signature varchar(50)   null,
    unionId          varchar(256)  null,
    mpOpenId         varchar(256)  null,
    userName         varchar(50)   null,
    userAvatar       varchar(1024) null,
    userProfile      varchar(512)  null,
    userRole         varchar(256)  null,
    join_type        tinyint       null,
    area_name        varchar(255)  null,
    area_code        varchar(50)   null,
    last_login_time  datetime      null,
    last_leave_time  bigint        null,
    createTime       datetime      null,
    updateTime       datetime      null,
    isDelete         tinyint       null
);

