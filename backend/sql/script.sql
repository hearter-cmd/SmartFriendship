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

create table if not exists app_like
(
    appId      bigint   null,
    userId     bigint   null,
    createTime datetime null
);

create table if not exists group_info
(
    group_id       bigint       null,
    group_name     varchar(20)  null,
    group_avatar   varchar(255) null,
    group_owner_id bigint       null,
    create_time    datetime     null,
    group_notice   varchar(500) null,
    join_type      tinyint      null,
    status         tinyint      null
);

create index group_info_group_id_index
    on group_info (group_id);

create table if not exists group_member
(
    groupId    bigint   null comment '群主键',
    userId     bigint   null comment '创建人ID',
    createTime datetime null comment '创建时间',
    updateTime datetime null comment '更新时间',
    isDelete   tinyint  null comment '逻辑删除标志位'
);

create table if not exists message
(
    id         int           null,
    contactId  bigint        null,
    userId     bigint        null,
    message    varchar(1024) null,
    type       tinyint       null,
    createTime datetime      null,
    updateTime datetime      null,
    isDelete   tinyint       null
);

create table if not exists model_dict_detail
(
    id          varchar(32)                        not null comment '主键'
        primary key,
    modelId     bigint                             not null comment '模型主键',
    modelApiKey varchar(255)                       null comment '模型的APIKEY',
    createBy    mediumtext                         not null comment '创建人',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateBy    mediumtext                         null comment '更新人',
    updateTime  datetime                           null comment '更新时间',
    deleteBy    mediumtext                         null comment '删除人',
    deleteTime  datetime                           null comment '删除时间',
    isDeleted   char     default '0'               not null comment '是否删除(0: 未删除, 1: 已删除)',
    constraint ai_model_type_dict_pk_2
        unique (modelApiKey)
)
    comment 'AI大模型详细信息';

create index ai_model_type_dict_pk
    on model_dict_detail (modelId);

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

create table if not exists question
(
    id              bigint auto_increment comment 'id'
        primary key,
    questionContent text                               null comment '题目内容（json格式）',
    appId           bigint                             not null comment '应用 id',
    userId          bigint                             not null comment '创建用户 id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除'
)
    comment '题目' collate = utf8mb4_unicode_ci;

create index idx_appId
    on question (appId);

create table if not exists scoring_result
(
    id               bigint auto_increment comment 'id'
        primary key,
    resultName       varchar(128)                       not null comment '结果名称，如物流师',
    resultDesc       text                               null comment '结果描述',
    resultPicture    varchar(1024)                      null comment '结果图片',
    resultProp       varchar(128)                       null comment '结果属性集合 JSON，如 [I,S,T,J]',
    resultScoreRange int                                null comment '结果得分范围，如 80，表示 80及以上的分数命中此结果',
    appId            bigint                             not null comment '应用 id',
    userId           bigint                             not null comment '创建用户 id',
    createTime       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete         tinyint  default 0                 not null comment '是否删除'
)
    comment '评分结果' collate = utf8mb4_unicode_ci;

create index idx_appId
    on scoring_result (appId);

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

create table if not exists sys_role_menu
(
    roleId bigint not null comment '角色ID',
    menuId bigint not null comment '菜单ID',
    primary key (roleId, menuId)
)
    comment '角色和菜单关联表';

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

create table if not exists sys_user_role
(
    userId bigint not null comment '用户ID',
    roleId bigint not null comment '角色ID',
    primary key (userId, roleId)
)
    comment '用户和角色关联表';

create table if not exists tag
(
    id         int         null,
    tag        varchar(50) null,
    parentId   int         null,
    isParent   tinyint     null,
    createTime datetime    null,
    updateTime datetime    null,
    isDelete   tinyint     null
);

create table if not exists user_answer
(
    id              bigint auto_increment
        primary key,
    appId           bigint                             not null comment '应用 id',
    appType         tinyint  default 0                 not null comment '应用类型（0-得分类，1-角色测评类）',
    scoringStrategy tinyint  default 0                 not null comment '评分策略（0-自定义，1-AI）',
    choices         text                               null comment '用户答案（JSON 数组）',
    resultId        bigint                             null comment '评分结果 id',
    resultName      varchar(128)                       null comment '结果名称，如物流师',
    resultDesc      text                               null comment '结果描述',
    resultPicture   varchar(1024)                      null comment '结果图标',
    resultScore     int                                null comment '得分',
    userId          bigint                             not null comment '用户 id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除'
)
    comment '用户答题记录' collate = utf8mb4_unicode_ci;

create index idx_appId
    on user_answer (appId);

create index idx_userId
    on user_answer (userId);

create table if not exists user_answer_0
(
    id              bigint auto_increment
        primary key,
    appId           bigint                             not null comment '应用 id',
    appType         tinyint  default 0                 not null comment '应用类型（0-得分类，1-角色测评类）',
    scoringStrategy tinyint  default 0                 not null comment '评分策略（0-自定义，1-AI）',
    choices         text                               null comment '用户答案（JSON 数组）',
    resultId        bigint                             null comment '评分结果 id',
    resultName      varchar(128)                       null comment '结果名称，如物流师',
    resultDesc      text                               null comment '结果描述',
    resultPicture   varchar(1024)                      null comment '结果图标',
    resultScore     int                                null comment '得分',
    userId          bigint                             not null comment '用户 id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除'
)
    comment '用户答题记录' collate = utf8mb4_unicode_ci;

create index idx_appId
    on user_answer_0 (appId);

create index idx_userId
    on user_answer_0 (userId);

create table if not exists user_answer_1
(
    id              bigint auto_increment
        primary key,
    appId           bigint                             not null comment '应用 id',
    appType         tinyint  default 0                 not null comment '应用类型（0-得分类，1-角色测评类）',
    scoringStrategy tinyint  default 0                 not null comment '评分策略（0-自定义，1-AI）',
    choices         text                               null comment '用户答案（JSON 数组）',
    resultId        bigint                             null comment '评分结果 id',
    resultName      varchar(128)                       null comment '结果名称，如物流师',
    resultDesc      text                               null comment '结果描述',
    resultPicture   varchar(1024)                      null comment '结果图标',
    resultScore     int                                null comment '得分',
    userId          bigint                             not null comment '用户 id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除'
)
    comment '用户答题记录' collate = utf8mb4_unicode_ci;

create index idx_appId
    on user_answer_1 (appId);

create index idx_userId
    on user_answer_1 (userId);

create table if not exists user_beauty
(
    id               bigint        null,
    userAccount      varchar(256)  null,
    userPassword     varchar(512)  null,
    email            varchar(50)   null,
    sex              tinyint       null,
    person_signature varchar(50)   null,
    unionId          varchar(256)  null,
    mpOpenId         varchar(256)  null,
    userName         varchar(50)   null,
    userAvatar       varchar(1024) null,
    userProfile      varchar(512)  null,
    userRole         varchar(256)  null,
    join_type        tinyint       null,
    area_name        varchar(255)  null,
    area_code        varchar(50)   null,
    last_login_time  datetime      null,
    last_leave_time  bigint        null,
    createTime       datetime      null,
    updateTime       datetime      null,
    isDelete         tinyint       null
);

create table if not exists user_contact
(
    user_id      bigint   null,
    contact_id   bigint   null,
    contact_type tinyint  null,
    create_time  datetime null,
    status       tinyint  null
);

create table if not exists user_contact_apply
(
    apply_id        int          null,
    apply_user_id   bigint       null,
    receive_user_id bigint       null,
    concat_type     tinyint(1)   null,
    concat_id       bigint       null,
    last_apply_time bigint       null,
    status          tinyint(1)   null,
    apply_info      varchar(100) null
);


