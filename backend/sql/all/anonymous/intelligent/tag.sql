create table if not exists tag
(
    id         int         null,
    tag        varchar(50) null,
    parentId   int         null,
    isParent   tinyint     null,
    createTime datetime    null,
    updateTime datetime    null,
    isDelete   tinyint     null
);

