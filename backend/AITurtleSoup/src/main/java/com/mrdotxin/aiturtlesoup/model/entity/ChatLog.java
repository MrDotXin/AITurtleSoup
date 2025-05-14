package com.mrdotxin.aiturtlesoup.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 聊天记录表
 */
@Data
@TableName("chatLog")
public class ChatLog {
    /**
     * 消息ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 游戏会话ID
     */
    private Long sessionId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 玩家ID(NULL表示系统消息)
     */
    private Long userId;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sentTime;

    /**
     * 是否是提问(0-否,1-是)
     */
    private Integer isQuestion;

    /**
     * 如果是回答, 回答的是哪个疑问
     */
    private Long referenceId;
}