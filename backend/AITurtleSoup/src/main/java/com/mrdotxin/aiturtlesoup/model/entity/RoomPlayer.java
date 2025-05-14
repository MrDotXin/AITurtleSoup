package com.mrdotxin.aiturtlesoup.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 房间玩家关联
 */
@TableName(value = "roomPlayer")
@Data
public class RoomPlayer implements Serializable {

    /**
     * 关联ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 玩家ID
     */
    private Long playerId;

    /**
     * 加入时间
     */
    private Date joinTime;

    /**
     * 是否观战者(0-否,1-是)
     */
    private Integer isSpectator;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}