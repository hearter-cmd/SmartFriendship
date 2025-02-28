create table if not exists user_contact
(
    user_id      bigint   null,
    contact_id   bigint   null,
    contact_type tinyint  null,
    create_time  datetime null,
    status       tinyint  null
);

