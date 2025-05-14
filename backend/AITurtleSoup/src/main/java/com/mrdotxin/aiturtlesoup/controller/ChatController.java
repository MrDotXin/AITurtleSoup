package com.mrdotxin.aiturtlesoup.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mrdotxin.aiturtlesoup.common.BaseResponse;
import com.mrdotxin.aiturtlesoup.common.ErrorCode;
import com.mrdotxin.aiturtlesoup.common.ResultUtils;
import com.mrdotxin.aiturtlesoup.constant.UserConstant;
import com.mrdotxin.aiturtlesoup.enums.ChatMessageTypeEnum;
import com.mrdotxin.aiturtlesoup.enums.RoomStatusEnum;
import com.mrdotxin.aiturtlesoup.exception.BusinessException;
import com.mrdotxin.aiturtlesoup.model.entity.*;
import com.mrdotxin.aiturtlesoup.service.*;
import com.mrdotxin.aiturtlesoup.utils.ArkUtils;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private GameSessionService gameSessionService;

    @Resource
    private ChatRoomService chatRoomService;

    @Resource
    private ChatLogService chatLogService;

    @Resource
    private TurtleSoupService turtleSoupService;

    @Resource
    private AIChatService aiChatService;

    // 测试接口, 只用来跑通项目流程
    @PostMapping("/room/{id}/postQueryById")
    public BaseResponse<ChatLog> postQueryById(@PathVariable("id") Long roomId, String message, HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        }

        ChatLog chatLog = new ChatLog();

        ChatRoom chatRoom = chatRoomService.getById(roomId);
        Long sessionId = chatRoom.getSessionId();

        chatLog.setIsQuestion(1);
        chatLog.setRoomId(roomId);
        chatLog.setUserId(user.getId());
        chatLog.setMessageType(ChatMessageTypeEnum.TEXT.getValue());
        chatLog.setContent(message);

        if (sessionId > 0) {
            chatLog.setSessionId(sessionId);
            chatLogService.save(chatLog);

            List<ChatLog> chatLogs = chatLogService.getChatLogAllBySessionId(sessionId);
            List<ChatMessage> messages = chatLogs.stream().map(log -> {
                return ChatMessage.builder()
                        .content(log.getContent())
                        .role(log.getIsQuestion() == 1 ? ChatMessageRole.USER : ChatMessageRole.ASSISTANT)
                        .build();
            }).collect(Collectors.toList());

            TurtleSoup turtleSoup = turtleSoupService.getById(chatRoom.getSoupId());
            messages.add(0, ArkUtils.asSystem("现在游戏开始了, 你只能按照规则回答问题"));
            messages.add(0, ArkUtils.asSystem("这是汤面: " + turtleSoup.getRiddle()));
            messages.add(0, ArkUtils.asSystem("这是汤底: " + turtleSoup.getSolution()));


            String done = aiChatService.doChat(messages);

            ChatLog response = new ChatLog();
            response.setSessionId(sessionId);
            response.setRoomId(roomId);
            response.setMessageType(ChatMessageTypeEnum.TEXT.getValue());
            response.setContent(done);
            response.setIsQuestion(0);
            response.setReferenceId(chatLog.getId());

            chatLogService.save(response);

            return ResultUtils.success(response);
        }

        return ResultUtils.success(null);
    }

    @PostMapping("/room/{id}/startGame")
    public BaseResponse<Long> startGame(@PathVariable("id") Long roomId) {

        ChatRoom chatRoom = chatRoomService.getById(roomId);
        if (chatRoom.getSessionId() > 0L) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "错误! 当前游戏进行中");
        }

        chatRoom.setStatus(RoomStatusEnum.STARTED.getValue());
        GameSession gameSession = new GameSession();
        gameSession.setSoupId(chatRoom.getSoupId());

        if (!gameSessionService.save(gameSession)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无法开始游戏");
        }

        chatRoom.setSessionId(gameSession.getId());

        chatRoomService.updateById(chatRoom);

        // 清空聊天记录
        chatLogService.remove(new UpdateWrapper<ChatLog>().eq("roomId", chatRoom.getId()));

        return ResultUtils.success(gameSession.getId());
    }

    @PostMapping("/room/{id}/endGame")
    public BaseResponse<Boolean> endGame(@PathVariable("id") Long roomId) {

        UpdateWrapper<ChatRoom> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", roomId);

        wrapper.set("sessionId", 0);
        wrapper.set("status", RoomStatusEnum.ENDED.getValue());


        if (!chatRoomService.update(wrapper)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无法执行结束游戏操作");
        }

        return ResultUtils.success(true);
    }
}
