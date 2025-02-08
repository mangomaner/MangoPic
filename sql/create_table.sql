-- �û���
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '�˺�',
    userPassword varchar(512)                           not null comment '����',
    userName     varchar(256)                           null comment '�û��ǳ�',
    userAvatar   varchar(1024)                          null comment '�û�ͷ��',
    userProfile  varchar(512)                           null comment '�û����',
    userRole     varchar(256) default 'user'            not null comment '�û���ɫ��user/admin',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '�༭ʱ��',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '����ʱ��',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '����ʱ��',
    isDelete     tinyint      default 0                 not null comment '�Ƿ�ɾ��',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
    ) comment '�û�' collate = utf8mb4_unicode_ci;
