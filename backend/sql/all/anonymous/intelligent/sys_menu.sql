create table if not exists sys_menu
(
    id         bigint auto_increment comment '菜单ID'
        primary key,
    name       varchar(50)              not null comment '菜单名称',
    code       varchar(255) default ''  not null comment '菜单代码',
    type       char(20)     default ''  null comment '菜单类型（M目录 C菜单BUTTON按钮）',
    parentId   bigint       default 0   null comment '父菜单ID',
    path       varchar(200) default ''  null comment '路由地址',
    redirect   varchar(255) default ''  null comment '重定向',
    icon       varchar(100) default '#' null comment '菜单图标',
    component  varchar(255)             null comment '组件路径',
    layout     varchar(255) default ''  not null comment '样式',
    isFrame    int          default 1   null comment '是否为外链（0是 1否）',
    isCache    int          default 0   null comment '是否缓存（0缓存 1不缓存）',
    visible    char         default '0' null comment '菜单状态（0隐藏 1显示）',
    enable     char         default '0' null comment '菜单状态（0停用 1正常）',
    remark     varchar(500) default ''  null comment '备注',
    orderNum   int          default 0   null comment '显示顺序',
    perms      varchar(100)             null comment '权限标识',
    createBy   varchar(64)  default ''  null comment '创建者',
    createTime datetime                 null comment '创建时间',
    updateBy   varchar(64)  default ''  null comment '更新者',
    updateTime datetime                 null comment '更新时间',
    isDeleted  char         default '0' not null comment '逻辑删除(0:未，1:已)'
)
    comment '菜单权限表';

