package com.mrdotxin.aiturtlesoup;

import com.mrdotxin.aiturtlesoup.service.AIChatService;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
// 请确保您已将 API Key 存储在环境变量 ARK_API_KEY 中
// 初始化Ark客户端，从环境变量中读取您的API Key

public class AiTurtleSoupApplicationTests {
    // 从环境变量中获取您的 API Key。此为默认方式，您可根据需要进行修改

    @Resource
    private AIChatService aiChatService;


    @Test
    public void test() {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(
                ChatMessage.builder()
                        .role(ChatMessageRole.SYSTEM)
                        .content("汤面: 一对夫妻搬进新家，每晚婴儿床都会传来哭声，但他们根本没有孩子。监控显示婴儿床始终空无一人。最终他们精神崩溃搬离，警察却在床垫下发现了可怕的东西。为什么？")
                        .build());

        chatMessages.add(
                ChatMessage.builder()
                        .role(ChatMessageRole.SYSTEM)
                        .content("汤底: 前任房主杀害了自己的婴儿，尸体被缝进床垫夹层。夫妻听到的“哭声”是腐烂尸体释放气体穿过缝隙的声音，床垫随温度变化轻微变形，触发监控误判为“空床”")
                        .build());

        chatMessages.add(
            ChatMessage.builder()
                    .role(ChatMessageRole.USER)
                    .content("[婴儿]是真的存在吗")
                    .build()
        );

        chatMessages.add(
            ChatMessage.builder()
                    .role(ChatMessageRole.ASSISTANT)
                    .content("是")
                    .build()
        );

        chatMessages.add(
            ChatMessage.builder()
                    .role(ChatMessageRole.USER)
                    .content("[婴儿]是真的存在于他们家床底下吗")
                    .build()
        );

        chatMessages.add(
            ChatMessage.builder()
                    .role(ChatMessageRole.ASSISTANT)
                    .content("是也不是")
                    .build()
        );

        chatMessages.add(
            ChatMessage.builder()
                    .role(ChatMessageRole.USER)
                    .content("[婴儿]是死的")
                    .build()
        );

        String done = aiChatService.doChat(chatMessages);
        System.out.println(done);
    }
}
