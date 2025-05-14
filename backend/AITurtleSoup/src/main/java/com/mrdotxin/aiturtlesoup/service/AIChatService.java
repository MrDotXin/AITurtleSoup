package com.mrdotxin.aiturtlesoup.service;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;

import java.util.List;

public interface AIChatService {

    /**
     *
     * @param chatMessages 历史对话
     * @return 对应的消息
     */
    String doChat(List<ChatMessage> chatMessages);
}
