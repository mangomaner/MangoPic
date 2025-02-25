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

-- ͼƬ��
create table if not exists picture
(
    id           bigint auto_increment comment 'id' primary key,
    url          varchar(512)                       not null comment 'ͼƬ url',
    name         varchar(128)                       not null comment 'ͼƬ����',
    introduction varchar(512)                       null comment '���',
    category     varchar(64)                        null comment '����',
    tags         varchar(512)                      null comment '��ǩ��JSON ���飩',
    picSize      bigint                             null comment 'ͼƬ���',
    picWidth     int                                null comment 'ͼƬ���',
    picHeight    int                                null comment 'ͼƬ�߶�',
    picScale     double                             null comment 'ͼƬ��߱���',
    picFormat    varchar(32)                        null comment 'ͼƬ��ʽ',
    userId       bigint                             not null comment '�����û� id',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '����ʱ��',
    editTime     datetime default CURRENT_TIMESTAMP not null comment '�༭ʱ��',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '����ʱ��',
    isDelete     tinyint  default 0                 not null comment '�Ƿ�ɾ��',
    INDEX idx_name (name),                 -- ��������ͼƬ���ƵĲ�ѯ����
    INDEX idx_introduction (introduction), -- ����ģ������ͼƬ���
    INDEX idx_category (category),         -- �������ڷ���Ĳ�ѯ����
    INDEX idx_tags (tags),                 -- �������ڱ�ǩ�Ĳ�ѯ����
    INDEX idx_userId (userId)              -- ���������û� ID �Ĳ�ѯ����
    ) comment 'ͼƬ' collate = utf8mb4_unicode_ci;

ALTER TABLE picture
    -- �������
    ADD COLUMN reviewStatus INT DEFAULT 0 NOT NULL COMMENT '���״̬��0-�����; 1-ͨ��; 2-�ܾ�',
    ADD COLUMN reviewMessage VARCHAR(512) NULL COMMENT '�����Ϣ',
    ADD COLUMN reviewerId BIGINT NULL COMMENT '����� ID',
    ADD COLUMN reviewTime DATETIME NULL COMMENT '���ʱ��';

-- �������� reviewStatus �е�����
CREATE INDEX idx_reviewStatus ON picture (reviewStatus);

ALTER TABLE picture
    -- �������
    ADD COLUMN thumbnailUrl varchar(512) NULL COMMENT '����ͼ url';

-- �ռ��
create table if not exists space
(
    id         bigint auto_increment comment 'id' primary key,
    spaceName  varchar(128)                       null comment '�ռ�����',
    spaceLevel int      default 0                 null comment '�ռ伶��0-��ͨ�� 1-רҵ�� 2-�콢��',
    maxSize    bigint   default 0                 null comment '�ռ�ͼƬ������ܴ�С',
    maxCount   bigint   default 0                 null comment '�ռ�ͼƬ���������',
    totalSize  bigint   default 0                 null comment '��ǰ�ռ���ͼƬ���ܴ�С',
    totalCount bigint   default 0                 null comment '��ǰ�ռ��µ�ͼƬ����',
    userId     bigint                             not null comment '�����û� id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '����ʱ��',
    editTime   datetime default CURRENT_TIMESTAMP not null comment '�༭ʱ��',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '����ʱ��',
    isDelete   tinyint  default 0                 not null comment '�Ƿ�ɾ��',
    -- �������
    index idx_userId (userId),        -- ���������û��Ĳ�ѯЧ��
    index idx_spaceName (spaceName),  -- �������ڿռ����ƵĲ�ѯЧ��
    index idx_spaceLevel (spaceLevel) -- �������ռ伶���ѯ��Ч��
    ) comment '�ռ�' collate = utf8mb4_unicode_ci;

-- �������
ALTER TABLE picture
    ADD COLUMN spaceId  bigint  null comment '�ռ� id��Ϊ�ձ�ʾ�����ռ䣩';

-- ��������
CREATE INDEX idx_spaceId ON picture (spaceId);


