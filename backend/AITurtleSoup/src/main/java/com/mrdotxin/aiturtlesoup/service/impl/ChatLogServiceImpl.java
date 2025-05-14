package com.mrdotxin.aiturtlesoup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrdotxin.aiturtlesoup.mapper.ChatLogMapper;
import com.mrdotxin.aiturtlesoup.model.entity.ChatLog;
import com.mrdotxin.aiturtlesoup.service.ChatLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLogServiceImpl extends ServiceImpl<ChatLogMapper, ChatLog> implements ChatLogService {


    @Override
    public List<ChatLog> getChatLogAllBySessionId(Long sessionId) {
        QueryWrapper<ChatLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sessionId", sessionId);

        return getBaseMapper().selectList(queryWrapper);
    }
}