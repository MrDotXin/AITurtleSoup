package com.mrdotxin.aiturtlesoup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrdotxin.aiturtlesoup.common.ErrorCode;
import com.mrdotxin.aiturtlesoup.constant.CommonConstant;
import com.mrdotxin.aiturtlesoup.constant.UserConstant;
import com.mrdotxin.aiturtlesoup.enums.RoomStatusEnum;
import com.mrdotxin.aiturtlesoup.exception.BusinessException;
import com.mrdotxin.aiturtlesoup.mapper.ChatRoomMapper;
import com.mrdotxin.aiturtlesoup.model.dto.chatRoom.ChatRoomCreateRequest;
import com.mrdotxin.aiturtlesoup.model.dto.chatRoom.ChatRoomQueryRequest;
import com.mrdotxin.aiturtlesoup.model.entity.ChatRoom;
import com.mrdotxin.aiturtlesoup.model.entity.User;
import com.mrdotxin.aiturtlesoup.service.ChatRoomService;
import com.mrdotxin.aiturtlesoup.utils.SqlUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomMapper, ChatRoom> implements ChatRoomService {


    @Override
    public Long createChatRoom(ChatRoomCreateRequest request, HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "你没登陆你怎么创建房间的? 嗯? tell me, why?");
        }

        ChatRoom chatRoom = new ChatRoom();

        chatRoom.setName(request.getName());
        chatRoom.setStatus(RoomStatusEnum.WAITING.getValue());
        chatRoom.setSoupId(request.getSoupId());
        chatRoom.setAnchorId(request.getAnchorId());

        boolean res = this.save(chatRoom);
        if (!res) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建失败!");
        }

        return chatRoom.getId();
    }

    @Override
    public QueryWrapper<ChatRoom> getQueryWrapper(ChatRoomQueryRequest queryRequest) {
        QueryWrapper<ChatRoom> queryWrapper = new QueryWrapper<>();


        String name = queryRequest.getName();
        String soupId = queryRequest.getSoupId();
        String status = queryRequest.getStatus();
        String sortField = queryRequest.getSortField();
        String sortOrder = queryRequest.getSortOrder();

        queryWrapper.eq(StringUtils.isNotBlank(status), "status", status);
        queryWrapper.eq(StringUtils.isNotBlank(soupId), "soupId", soupId);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);


        queryWrapper.orderBy(
                SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField
        );

        return queryWrapper;
    }

}