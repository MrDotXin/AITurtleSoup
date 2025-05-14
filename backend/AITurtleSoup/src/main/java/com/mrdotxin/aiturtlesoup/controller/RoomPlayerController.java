package com.mrdotxin.aiturtlesoup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrdotxin.aiturtlesoup.model.entity.RoomPlayer;
import com.mrdotxin.aiturtlesoup.service.RoomPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-player")
public class RoomPlayerController {

    @Autowired
    private RoomPlayerService roomPlayerService;

    @GetMapping
    public List<RoomPlayer> getAllRoomPlayers() {
        return roomPlayerService.list();
    }

    @GetMapping("/{id}")
    public RoomPlayer getRoomPlayerById(@PathVariable Long id) {
        return roomPlayerService.getById(id);
    }

    @PostMapping
    public boolean createRoomPlayer(@RequestBody RoomPlayer roomPlayer) {
        return roomPlayerService.save(roomPlayer);
    }

    @PutMapping("/{id}")
    public boolean updateRoomPlayer(@PathVariable Long id, @RequestBody RoomPlayer roomPlayer) {
        roomPlayer.setId(id);
        return roomPlayerService.updateById(roomPlayer);
    }

    @DeleteMapping("/{id}")
    public boolean deleteRoomPlayer(@PathVariable Long id) {
        return roomPlayerService.removeById(id);
    }

    @GetMapping("/page")
    public Page<RoomPlayer> getRoomPlayersByPage(@RequestParam int page, @RequestParam int size) {
        return roomPlayerService.page(new Page<>(page, size));
    }

    @GetMapping("/search")
    public List<RoomPlayer> searchRoomPlayers(@RequestParam String keyword) {
        QueryWrapper<RoomPlayer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        return roomPlayerService.list(queryWrapper);
    }
}