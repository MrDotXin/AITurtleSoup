package com.mrdotxin.aiturtlesoup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrdotxin.aiturtlesoup.common.BaseResponse;
import com.mrdotxin.aiturtlesoup.common.PageRequest;
import com.mrdotxin.aiturtlesoup.common.ResultUtils;
import com.mrdotxin.aiturtlesoup.model.dto.turtleSoup.TurtleSoupCreateRequest;
import com.mrdotxin.aiturtlesoup.model.dto.turtleSoup.TurtleSoupQueryRequest;
import com.mrdotxin.aiturtlesoup.model.entity.TurtleSoup;
import com.mrdotxin.aiturtlesoup.service.TurtleSoupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turtle-soup")
public class TurtleSoupController {

    @Autowired
    private TurtleSoupService turtleSoupService;

    @GetMapping
    public BaseResponse<List<TurtleSoup>> getAllTurtleSoups() {
        return ResultUtils.success(turtleSoupService.list());
    }

    @GetMapping("/{id}")
    public BaseResponse<TurtleSoup> getTurtleSoupById(@PathVariable Long id) {

        return ResultUtils.success(turtleSoupService.getById(id));
    }

    /**
     * 根据条件分页查询乌龟汤列表
     * @param request 查询条件封装对象
     * @return 分页查询结果
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<TurtleSoup>> listTurtleSoupByPages(@RequestBody TurtleSoupQueryRequest request) {
        long size = request.getPageSize();
        long current = request.getCurrent();

        Page<TurtleSoup> turtleSoupPage = turtleSoupService.page(
                new Page<>(current, size),
                turtleSoupService.getQueryWrapper(request)
        );

        return ResultUtils.success(turtleSoupPage);
    }

    @PostMapping
    public BaseResponse<Boolean> createTurtleSoup(@RequestBody TurtleSoupCreateRequest turtleSoup) {

        TurtleSoup soup = new TurtleSoup();
        BeanUtils.copyProperties(turtleSoup, soup);

        return ResultUtils.success(turtleSoupService.save(soup));
    }

    @PutMapping("/{id}")
    public boolean updateTurtleSoup(@PathVariable Long id, @RequestBody TurtleSoup turtleSoup) {
        turtleSoup.setId(id);
        return turtleSoupService.updateById(turtleSoup);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTurtleSoup(@PathVariable Long id) {
        return turtleSoupService.removeById(id);
    }

    @GetMapping("/page")
    public BaseResponse<Page<TurtleSoup>> getTurtleSoupsByPage(@RequestParam int page, @RequestParam int size) {
        return ResultUtils.success(turtleSoupService.page(new Page<>(page, size)));
    }

    @GetMapping("/search")
    public List<TurtleSoup> searchTurtleSoups(@RequestParam String keyword) {
        QueryWrapper<TurtleSoup> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        return turtleSoupService.list(queryWrapper);
    }
}