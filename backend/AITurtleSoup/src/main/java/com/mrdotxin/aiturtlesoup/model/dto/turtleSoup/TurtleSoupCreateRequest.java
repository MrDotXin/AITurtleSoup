package com.mrdotxin.aiturtlesoup.model.dto.turtleSoup;

import lombok.Data;

@Data
public class TurtleSoupCreateRequest {


    /**
     * 标题
     */
    private String title;

    /**
     * 谜语
     */
    private String riddle;


    /**
     * 解
     */
    private String solution;
}
