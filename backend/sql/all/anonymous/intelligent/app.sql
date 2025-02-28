create table if not exists app
(
    id              bigint auto_increment comment 'id'
        primary key,
    appName         varchar(128)                       not null comment '应用名',
    appDesc         varchar(2048)                      null comment '应用描述',
    appIcon         varchar(1024)                      null comment '应用图标',
    appType         tinyint  default 0                 not null comment '应用类型（0-得分类，1-测评类）',
    scoringStrategy tinyint  default 0                 not null comment '评分策略（0-自定义，1-AI）',
    reviewStatus    int      default 0                 not null comment '审核状态：0-待审核, 1-通过, 2-拒绝',
    reviewMessage   varchar(512)                       null comment '审核信息',
    reviewerId      bigint                             null comment '审核人 id',
    reviewTime      datetime                           null comment '审核时间',
    userId          bigint                             not null comment '创建用户 id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除'
)
    comment '应用' collate = utf8mb4_unicode_ci;

create index idx_appName
    on app (appName);

