package com.mrdotxin.aiturtlesoup.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrdotxin.aiturtlesoup.model.dto.chatRoom.ChatRoomCreateRequest;
import com.mrdotxin.aiturtlesoup.model.dto.chatRoom.ChatRoomQueryRequest;
import com.mrdotxin.aiturtlesoup.model.entity.ChatRoom;

import javax.servlet.http.HttpServletRequest;

public interface ChatRoomService extends IService<ChatRoom> {

    /**
     * 创建聊天室
     * @param request 请求体
     * @param httpServletRequest 请求
     * @return 聊天室id
     */
    Long createChatRoom(ChatRoomCreateRequest request, HttpServletRequest httpServletRequest);


    /**
     * 获取查询条件
     */
    QueryWrapper<ChatRoom> getQueryWrapper(ChatRoomQueryRequest queryRequest);
}