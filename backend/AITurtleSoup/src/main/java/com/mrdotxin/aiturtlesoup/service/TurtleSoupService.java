package com.mrdotxin.aiturtlesoup.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrdotxin.aiturtlesoup.model.dto.turtleSoup.TurtleSoupQueryRequest;
import com.mrdotxin.aiturtlesoup.model.entity.TurtleSoup;

public interface TurtleSoupService extends IService<TurtleSoup> {


    /**
     * 获取查询条件构造器
     * @param userQueryRequest 查询条件封装对象
     * @return 查询条件构造器
     */
    QueryWrapper<TurtleSoup> getQueryWrapper(TurtleSoupQueryRequest userQueryRequest);
}