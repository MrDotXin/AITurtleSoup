    package com.mrdotxin.aiturtlesoup.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 游戏会话表
 */
@Data
@TableName("gameSession")
public class GameSession {
    /**
     * 会话ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 使用的预制汤的id
     */
    private Long soupId;

    /**
     * 开始时间
     */
    private Date startedTime;

    /**
     * 结束时间
     */
    private Date endedTime;
}