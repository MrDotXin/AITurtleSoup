package com.mrdotxin.aiturtlesoup.model.dto.turtleSoup;

import java.io.Serializable;

import com.mrdotxin.aiturtlesoup.common.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TurtleSoupQueryRequest extends PageRequest implements Serializable  {

    /**
     * 标题
     */
    private String title;

    /**
     * 谜语
     */
    private String riddle;

    /**
     * 难度
     */
    private Integer difficulty;

    private static final long serialVersionUID = 1L;
}
