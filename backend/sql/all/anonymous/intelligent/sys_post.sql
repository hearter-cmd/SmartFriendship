create table if not exists sys_post
(
    postId     bigint auto_increment comment '岗位ID'
        primary key,
    postCode   varchar(64)            not null comment '岗位编码',
    postName   varchar(50)            not null comment '岗位名称',
    postSort   int                    not null comment '显示顺序',
    status     char                   not null comment '状态（0正常 1停用）',
    createBy   varchar(64) default '' null comment '创建者',
    createTime datetime               null comment '创建时间',
    updateBy   varchar(64) default '' null comment '更新者',
    updateTime datetime               null comment '更新时间',
    remark     varchar(500)           null comment '备注'
)
    comment '岗位信息表';

