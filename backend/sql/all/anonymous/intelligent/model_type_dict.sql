create table if not exists model_type_dict
(
    id          varchar(32)                        not null comment '主键'
        primary key,
    modelName   varchar(64)                        not null comment '模型名称',
    modelCode   varchar(10)                        not null comment '模型代码',
    modelApiKey varchar(255)                       null comment '模型的APIKEY',
    modelDesc   text                               null comment '模型描述',
    createBy    mediumtext                         not null comment '创建人',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateBy    mediumtext                         null comment '更新人',
    updateTime  datetime                           null comment '更新时间',
    deleteBy    mediumtext                         null comment '删除人',
    deleteTime  datetime                           null comment '删除时间',
    isDeleted   char     default '0'               not null comment '是否删除(0: 未删除, 1: 已删除)',
    constraint ai_model_type_dict_pk
        unique (modelCode),
    constraint ai_model_type_dict_pk_2
        unique (modelApiKey)
)
    comment 'AI大模型类型';

