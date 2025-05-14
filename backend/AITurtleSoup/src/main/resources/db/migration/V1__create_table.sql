# 数据库初始化
-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 房间信息
CREATE TABLE IF NOT EXISTS chatRoom (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '房间ID',
    name            VARCHAR(256) NOT NULL COMMENT '房间名(汤名)',
    avatar          VARCHAR(256) COMMENT '房间头像URL',
    status          ENUM('waiting', 'started', 'ended') NOT NULL DEFAULT 'waiting' COMMENT '房间状态',
    sessionId       BIGINT NOT NULL  DEFAULT  0 COMMENT '当前活跃游戏会话ID',
    soupId          BIGINT NOT NULL             COMMENT '当前预制汤的ID',
    maxPlayers      INT NOT NULL DEFAULT 10     COMMENT '最大玩家数',
    currentPlayers  INT NOT NULL DEFAULT 0      COMMENT '当前玩家数',
    createTime      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updateTime      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    isDeleted       TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
    anchorId        BIGINT COMMENT '主持人ID, -1表示AI主持',
    INDEX idx_status (status),
    INDEX idx_anchor (anchorId)
) COMMENT '游戏房间表' COLLATE = utf8mb4_unicode_ci;

-- 游戏会话表
CREATE TABLE IF NOT EXISTS gameSession (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '会话ID',
    soupId          BIGINT         COMMENT '使用的预制汤的id',
    startedTime     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT  '开始时间',
    endedTime       TIMESTAMP NULL COMMENT '结束时间',
    INDEX idx_time (startedTime, endedTime)
) COMMENT '游戏会话表' COLLATE = utf8mb4_unicode_ci;

-- 房间玩家关联表
CREATE TABLE IF NOT EXISTS roomPlayer (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联ID',
    roomId              BIGINT NOT NULL COMMENT '房间ID',
    userId            BIGINT NOT NULL COMMENT '玩家ID',
    joinedTime          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    isSpectator         TINYINT NOT NULL DEFAULT 0 COMMENT '是否观战者(0-否,1-是)',
    FOREIGN KEY (roomId) REFERENCES chatRoom(id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE,
    UNIQUE INDEX uk_room_player (roomId, userId)
) COMMENT '房间玩家关联表' COLLATE = utf8mb4_unicode_ci;

-- 聊天记录表
CREATE TABLE IF NOT EXISTS chatLog (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '消息ID',
    sessionId       BIGINT          COMMENT '游戏会话ID',
    roomId          BIGINT NOT NULL COMMENT '房间ID',
    userId        BIGINT          COMMENT '玩家ID(NULL表示系统消息)',
    messageType    ENUM('text', 'image', 'system') NOT NULL DEFAULT 'text' COMMENT '消息类型',
    content         TEXT NOT NULL COMMENT '消息内容',
    sentTime        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    isQuestion      TINYINT NOT NULL DEFAULT 0 COMMENT '是否是提问(0-否,1-是)',
    referenceId     BIGINT NULL COMMENT '引用的消息ID(回复/关联)',

    INDEX idx_session (sessionId),
    INDEX idx_player (userId),
    INDEX idx_time (sentTime)
) COMMENT '聊天记录表' COLLATE = utf8mb4_unicode_ci;

-- 海龟汤内容表
CREATE TABLE turtleSoup (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(256) NOT NULL COMMENT '汤题名称',
    riddle      TEXT NOT NULL COMMENT '汤面(谜题)',
    solution    TEXT NOT NULL COMMENT '汤底(解答)',
    difficulty  TINYINT COMMENT '难度等级1-5',
    category    VARCHAR(64) COMMENT '分类',
    createdTime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_category (category),
    INDEX idx_difficulty (difficulty)
);