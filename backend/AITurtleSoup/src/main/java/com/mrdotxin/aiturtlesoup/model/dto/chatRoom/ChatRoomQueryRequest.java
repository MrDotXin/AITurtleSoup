package com.mrdotxin.aiturtlesoup.model.dto.chatRoom;

import com.mrdotxin.aiturtlesoup.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatRoomQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private String name;

    /**
     * 海龟汤
     */
    private String soupId;

    /**
     * 房间状态
     */
    private String status;

    private static final long serialVersionUID = 1L;
}
