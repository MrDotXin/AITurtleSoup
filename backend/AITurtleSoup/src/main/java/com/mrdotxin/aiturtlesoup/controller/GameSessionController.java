package com.mrdotxin.aiturtlesoup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrdotxin.aiturtlesoup.model.entity.GameSession;
import com.mrdotxin.aiturtlesoup.service.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-session")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;

    @GetMapping
    public List<GameSession> getAllGameSessions() {
        return gameSessionService.list();
    }

    @GetMapping("/{id}")
    public GameSession getGameSessionById(@PathVariable Long id) {
        return gameSessionService.getById(id);
    }

    @PostMapping
    public boolean createGameSession(@RequestBody GameSession gameSession) {
        return gameSessionService.save(gameSession);
    }

    @PutMapping("/{id}")
    public boolean updateGameSession(@PathVariable Long id, @RequestBody GameSession gameSession) {
        gameSession.setId(id);
        return gameSessionService.updateById(gameSession);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGameSession(@PathVariable Long id) {
        return gameSessionService.removeById(id);
    }

    @GetMapping("/page")
    public Page<GameSession> getGameSessionsByPage(@RequestParam int page, @RequestParam int size) {
        return gameSessionService.page(new Page<>(page, size));
    }

    @GetMapping("/search")
    public List<GameSession> searchGameSessions(@RequestParam String keyword) {
        QueryWrapper<GameSession> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        return gameSessionService.list(queryWrapper);
    }
}