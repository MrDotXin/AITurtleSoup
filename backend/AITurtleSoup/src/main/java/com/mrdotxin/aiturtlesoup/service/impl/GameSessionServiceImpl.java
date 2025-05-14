package com.mrdotxin.aiturtlesoup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrdotxin.aiturtlesoup.mapper.GameSessionMapper;
import com.mrdotxin.aiturtlesoup.model.entity.GameSession;
import com.mrdotxin.aiturtlesoup.service.GameSessionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GameSessionServiceImpl extends ServiceImpl<GameSessionMapper, GameSession> implements GameSessionService {

    @GetMapping("/session/byRoomId")
    public  GameSession getGameSessionByRoomId(@RequestParam Long roomId) {
        QueryWrapper<GameSession> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("roomId", roomId);
        queryWrapper.orderByDesc("createTime");

        return this.getOne(queryWrapper);
    }


}