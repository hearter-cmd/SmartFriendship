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

