create table if not exists sys_role
(
    roleId            bigint auto_increment comment '角色ID'
        primary key,
    roleName          varchar(30)             not null comment '角色名称',
    roleKey           varchar(100)            not null comment '角色权限字符串',
    roleSort          int                     not null comment '显示顺序',
    dataScope         char        default '1' null comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    menuCheckStrictly tinyint(1)  default 1   null comment '菜单树选择项是否关联显示',
    deptCheckStrictly tinyint(1)  default 1   null comment '部门树选择项是否关联显示',
    enable            char                    not null comment '角色状态（0正常 1停用）',
    isDelete          char        default '0' null comment '删除标志（0代表存在 2代表删除）',
    createBy          varchar(64) default ''  null comment '创建者',
    createTime        datetime                null comment '创建时间',
    updateBy          varchar(64) default ''  null comment '更新者',
    updateTime        datetime                null comment '更新时间',
    remark            varchar(500)            null comment '备注'
)
    comment '角色信息表';

create index sys_role_pk
    on sys_role (roleKey);

