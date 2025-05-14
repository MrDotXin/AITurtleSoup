package com.mrdotxin.aiturtlesoup.model.dto.chat;

import lombok.Data;

@Data
public class ChatRequest {

    private Long roomId;

    private Long sessionId;

    private Long userId;

    private String message;
}
