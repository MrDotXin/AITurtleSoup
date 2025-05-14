package com.mrdotxin.aiturtlesoup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrdotxin.aiturtlesoup.model.entity.ChatLog;

import java.util.List;

public interface ChatLogService extends IService<ChatLog> {

    /**
     * 得到当前会话的所有历史记录
     * @param sessionId sessionId
     * @return
     */
    List<ChatLog> getChatLogAllBySessionId(Long sessionId);
}