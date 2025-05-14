package com.mrdotxin.aiturtlesoup.utils;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;

public class ArkUtils {


    public static ChatMessage asSystem(String message) {
        return ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content(message)
                .build();
    }

    public static ChatMessage asUser(String message) {
        return ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(message)
                .build();
    }

    public static ChatMessage asASSISTANT(String message) {
        return ChatMessage.builder()
                .role(ChatMessageRole.ASSISTANT)
                .content(message)
                .build();
    }
}
