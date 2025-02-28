create table if not exists sys_dict_data
(
    dictCode   bigint auto_increment comment '字典编码'
        primary key,
    dictSort   int          default 0   null comment '字典排序',
    dictLabel  varchar(100) default ''  null comment '字典标签',
    dictValue  varchar(100) default ''  null comment '字典键值',
    dictType   varchar(100) default ''  null comment '字典类型',
    cssClass   varchar(100)             null comment '样式属性（其他样式扩展）',
    listClass  varchar(100)             null comment '表格回显样式',
    isDefault  char         default 'N' null comment '是否默认（Y是 N否）',
    status     char         default '0' null comment '状态（0正常 1停用）',
    createBy   varchar(64)  default ''  null comment '创建者',
    createTime datetime                 null comment '创建时间',
    updateBy   varchar(64)  default ''  null comment '更新者',
    updateTime datetime                 null comment '更新时间',
    remark     varchar(500)             null comment '备注'
)
    comment '字典数据表';

