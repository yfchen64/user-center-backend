-- auto-generated definition
create table user
(
    id           bigint auto_increment comment '主键'
        primary key,
    username     varchar(256)                           null,
    userAccount  varchar(256)                           not null comment '登录账号',
    userPassword varchar(256)                           null,
    avatarUrl    varchar(256)                           null comment '头像',
    gender       tinyint                                null comment '性别',
    address      varchar(256)                           null comment '地址',
    phone        varchar(256)                           null comment '手机号',
    email        varchar(256) default '0'               not null comment '邮箱',
    isValid      tinyint      default 0                 not null comment '是否有效(0-无效, 1-有效)',
    update_time  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    isDelete     tinyint      default 0                 not null comment '是否删除(0-未删, 1-已删)',
    userRole     int          default 0                 not null comment '用户角色 0-普通用户 1-管理员',
    planetCode   varchar(512)                           null comment '星球编号'
)
    comment '用户表信息';

