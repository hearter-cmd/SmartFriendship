create table if not exists sys_role_menu
(
    roleId bigint not null comment '角色ID',
    menuId bigint not null comment '菜单ID',
    primary key (roleId, menuId)
)
    comment '角色和菜单关联表';

