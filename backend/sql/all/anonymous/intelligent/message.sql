create table if not exists message
(
    id         int           null,
    contactId  bigint        null,
    userId     bigint        null,
    message    varchar(1024) null,
    type       tinyint       null,
    createTime datetime      null,
    updateTime datetime      null,
    isDelete   tinyint       null
);

