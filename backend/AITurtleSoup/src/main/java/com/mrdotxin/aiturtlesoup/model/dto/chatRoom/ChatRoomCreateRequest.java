package com.mrdotxin.aiturtlesoup.model.dto.chatRoom;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 游戏房间表
 */
@Data
public class ChatRoomCreateRequest {

    /**
     * 房间名(汤名)
     */
    private String name;

    /**
     * 房间头像URL
     */
    private String avatar;

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
     * 主持人ID，NULL表示AI主持
     */
    private Long anchorId;
}