package com.mrdotxin.aiturtlesoup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrdotxin.aiturtlesoup.common.BaseResponse;
import com.mrdotxin.aiturtlesoup.common.ResultUtils;
import com.mrdotxin.aiturtlesoup.enums.RoomStatusEnum;
import com.mrdotxin.aiturtlesoup.model.dto.chatRoom.ChatRoomCreateRequest;
import com.mrdotxin.aiturtlesoup.model.dto.chatRoom.ChatRoomQueryRequest;
import com.mrdotxin.aiturtlesoup.model.dto.turtleSoup.TurtleSoupQueryRequest;
import com.mrdotxin.aiturtlesoup.model.entity.ChatRoom;
import com.mrdotxin.aiturtlesoup.model.entity.TurtleSoup;
import com.mrdotxin.aiturtlesoup.service.ChatLogService;
import com.mrdotxin.aiturtlesoup.service.ChatRoomService;
import com.mrdotxin.aiturtlesoup.service.GameSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/chat-room")
public class ChatRoomController {
    @Resource
    private ChatRoomService chatRoomService;

    @GetMapping("/{id}")
    public BaseResponse<ChatRoom> getChatRoomById(@PathVariable Long id) {
        return ResultUtils.success(chatRoomService.getById(id));
    }

    @PostMapping
    public BaseResponse<Long> createChatRoom(@RequestBody ChatRoomCreateRequest request, HttpServletRequest httpServletRequest) {
        return ResultUtils.success(chatRoomService.createChatRoom(request, httpServletRequest));
    }

    @PostMapping("/{id}/updateTurtleSoup")
    public BaseResponse<Boolean> updateTurtleSoup(@PathVariable("id") Long id, @RequestParam("soupId") Long soupId) {
        UpdateWrapper<ChatRoom> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("soupId", soupId);
        wrapper.set("status", RoomStatusEnum.WAITING.getValue());

        return ResultUtils.success(chatRoomService.update(wrapper));
    }

    @PutMapping("/{id}/update")
    public BaseResponse<Boolean> updateChatRoom(@PathVariable Long id, @RequestBody ChatRoom chatRoom) {
        chatRoom.setId(id);
        return ResultUtils.success(chatRoomService.updateById(chatRoom));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteChatRoom(@PathVariable Long id) {
        return ResultUtils.success(chatRoomService.removeById(id));
    }

    @PostMapping("/list/page")
    public BaseResponse<Page<ChatRoom>> listChatRoomByPages(@RequestBody ChatRoomQueryRequest request) {
        long size = request.getPageSize();
        long current = request.getCurrent();

        Page<ChatRoom> chatRoomSoupPage = chatRoomService.page(
                new Page<>(current, size),
                chatRoomService.getQueryWrapper(request)
        );

        return ResultUtils.success(chatRoomSoupPage);
    }

}