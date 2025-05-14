package com.mrdotxin.aiturtlesoup.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 海龟汤内容
 */
@TableName(value = "turtleSoup")
@Data
public class TurtleSoup implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 汤题名称
     */
    private String title;

    /**
     * 汤面(谜题)
     */
    private String riddle;

    /**
     * 汤底(解答)
     */
    private String solution;

    /**
     * 难度等级1-5
     */
    private Integer difficulty;

    /**
     * 分类
     */
    private String category;

    /**
     * 创建时间
     */
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}