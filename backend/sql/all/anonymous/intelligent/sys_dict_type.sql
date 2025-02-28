create table if not exists sys_dict_type
(
    dictId     bigint auto_increment comment '字典主键'
        primary key,
    dictName   varchar(100) default ''  null comment '字典名称',
    dictType   varchar(100) default ''  null comment '字典类型',
    status     char         default '0' null comment '状态（0正常 1停用）',
    createBy   varchar(64)  default ''  null comment '创建者',
    createTime datetime                 null comment '创建时间',
    updateBy   varchar(64)  default ''  null comment '更新者',
    updateTime datetime                 null comment '更新时间',
    remark     varchar(500)             null comment '备注',
    constraint dict_type
        unique (dictType)
)
    comment '字典类型表';

