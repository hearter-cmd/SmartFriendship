create table if not exists group_member
(
    groupId    bigint   null comment '群主键',
    userId     bigint   null comment '创建人ID',
    createTime datetime null comment '创建时间',
    updateTime datetime null comment '更新时间',
    isDelete   tinyint  null comment '逻辑删除标志位'
);

