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

