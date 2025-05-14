package com.mrdotxin.aiturtlesoup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrdotxin.aiturtlesoup.common.BaseResponse;
import com.mrdotxin.aiturtlesoup.common.ErrorCode;
import com.mrdotxin.aiturtlesoup.common.ResultUtils;
import com.mrdotxin.aiturtlesoup.model.entity.ChatLog;
import com.mrdotxin.aiturtlesoup.model.entity.ChatRoom;
import com.mrdotxin.aiturtlesoup.service.ChatLogService;
import com.mrdotxin.aiturtlesoup.service.ChatRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/chat-log")
public class ChatLogController {

    @Autowired
    private ChatLogService chatLogService;

    @Resource
    private ChatRoomService chatRoomService;

    @GetMapping("/room/{roomId}/logs/all")
    public BaseResponse<List<ChatLog>> getAllGameChatLogs(@RequestParam Long roomId) {
        ChatRoom chatRoom = chatRoomService.getById(roomId);
        Long gameId = chatRoom.getSessionId();

        if (gameId > 0) {
            QueryWrapper<ChatLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sessionId", gameId);
            List<ChatLog> chatLogs = chatLogService.list(queryWrapper);
            return ResultUtils.success(chatLogs);
        }

        return ResultUtils.success(new ArrayList<ChatLog>());
    }
    
}