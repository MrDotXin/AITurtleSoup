package com.mrdotxin.aiturtlesoup.service.impl;

import com.mrdotxin.aiturtlesoup.service.AIChatService;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class AIChatServiceImpl implements AIChatService {

        @Resource
        private ArkService arkService;

        @Value("${ai.largeText}")
        private String prompts;

        public String doChat(List<ChatMessage> chatMessages) {
        List<ChatMessage> messages = new LinkedList<>();
        messages.add(
                ChatMessage.builder()
                        .role(ChatMessageRole.SYSTEM)
                        .content(prompts)
                        .build());
        messages.addAll(chatMessages);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                // 指定您创建的方舟推理接入点 ID，此处已帮您修改为您的推理接入点 ID
                .model("deepseek-v3-250324")
                .messages(messages)
                .build();

        StringBuilder responseBuilder = new StringBuilder();
        arkService.createChatCompletion(chatCompletionRequest)
                .getChoices()
                .forEach(
                        choice -> responseBuilder.append(choice.getMessage().getContent())
                );

        return responseBuilder.toString();
        }
}
