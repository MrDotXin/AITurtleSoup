package com.mrdotxin.aiturtlesoup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrdotxin.aiturtlesoup.constant.CommonConstant;
import com.mrdotxin.aiturtlesoup.mapper.TurtleSoupMapper;
import com.mrdotxin.aiturtlesoup.model.dto.turtleSoup.TurtleSoupQueryRequest;
import com.mrdotxin.aiturtlesoup.model.entity.TurtleSoup;
import com.mrdotxin.aiturtlesoup.service.TurtleSoupService;
import com.mrdotxin.aiturtlesoup.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TurtleSoupServiceImpl extends ServiceImpl<TurtleSoupMapper, TurtleSoup> implements TurtleSoupService {

    @Override
    public QueryWrapper<TurtleSoup> getQueryWrapper(TurtleSoupQueryRequest queryRequest) {
        QueryWrapper<TurtleSoup> queryWrapper = new QueryWrapper<>();

        String title = queryRequest.getTitle();
        String riddle = queryRequest.getRiddle();
        Integer difficulty = queryRequest.getDifficulty();
        String sortField = queryRequest.getSortField();
        String sortOrder = queryRequest.getSortOrder();


        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(title), "riddle", riddle);

        queryWrapper.orderBy(
                SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField
        );

        return queryWrapper;
    }
}