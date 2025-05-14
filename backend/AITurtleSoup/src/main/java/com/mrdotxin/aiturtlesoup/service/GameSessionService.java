package com.mrdotxin.aiturtlesoup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrdotxin.aiturtlesoup.model.entity.GameSession;
import org.springframework.web.bind.annotation.RequestParam;

public interface GameSessionService extends IService<GameSession> {

    /**
     * 根据房间号获取游戏会话
     * @param roomId 房间号
     * @return
     */
    GameSession getGameSessionByRoomId(Long roomId);
}