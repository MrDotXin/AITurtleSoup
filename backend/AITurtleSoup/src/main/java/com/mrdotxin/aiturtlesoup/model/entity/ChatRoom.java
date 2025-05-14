package com.mrdotxin.aiturtlesoup.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 游戏房间表
 */
@Data
@TableName("chatRoom")
public class ChatRoom {
    /**
     * 房间ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 房间名(汤名)
     */
    private String name;

    /**
     * 房间头像URL
     */
    private String avatar;

    /**
     * 房间状态
     */
    private String status;

    /**
     * 当前游戏会话ID
     */
    private Long sessionId;

    /**
     * 当前选择的汤ID
     */
    private Long soupId;

    /**
     * 最大玩家数
     */
    private Integer maxPlayers;

    /**
     * 当前玩家数
     */
    private Integer currentPlayers;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-否,1-是)
     */
    private Integer isDeleted;

    /**
     * 主持人ID，NULL表示AI主持
     */
    private Long anchorId;
}