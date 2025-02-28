create table if not exists sys_user_role
(
    userId bigint not null comment '用户ID',
    roleId bigint not null comment '角色ID',
    primary key (userId, roleId)
)
    comment '用户和角色关联表';

