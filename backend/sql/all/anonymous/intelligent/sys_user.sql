create table if not exists sys_user
(
    id              bigint                   not null comment '用户主键'
        primary key,
    deptId          bigint                   null comment '部门ID',
    userAccount     varchar(256)             null comment '用户账号',
    userType        varchar(2)  default '00' null comment '用户类型（00系统用户）',
    userPassword    varchar(512)             null comment '用户密码',
    email           varchar(50) default ''   null comment '用户邮箱',
    sex             char        default '0'  null comment '用户性别（0男 1女 2未知）',
    personSignature varchar(50)              null comment '用户签名',
    unionId         varchar(256)             null,
    mpOpenId        varchar(256)             null comment '微信OPENID',
    userName        varchar(50)              null comment '用户名',
    userAvatar      varchar(1024)            null comment '用户头像url',
    userProfile     varchar(512)             null comment '用户简介',
    userRole        varchar(256)             null comment '用户角色',
    joinType        tinyint                  null comment '添加好友的方式 0 : 直接添加; 1 : 同意后添加',
    ip              varchar(50)              null comment '最后一次登录的IP地址',
    areaName        varchar(255)             null comment '地区名称',
    areaCode        varchar(50)              null comment '地区代码',
    tags            varchar(1024)            null comment '用户兴趣标签',
    enable          char        default '1'  not null comment '是否启用（1:启用，0:禁用）',
    lastLoginTime   datetime                 null comment '最后登录时间',
    lastLeaveTime   bigint                   null comment '最后下线时间',
    createTime      datetime                 null comment '创建时间',
    updateTime      datetime                 null comment '更新时间',
    isDelete        tinyint                  null comment '逻辑删除标志位',
    createBy        varchar(64) default ''   null comment '创建者',
    updateBy        varchar(64) default ''   null comment '更新者',
    remark          varchar(500)             null comment '备注'
);

