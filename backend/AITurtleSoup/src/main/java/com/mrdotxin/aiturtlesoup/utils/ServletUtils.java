package com.mrdotxin.aiturtlesoup.utils;

import com.mrdotxin.aiturtlesoup.common.ErrorCode;
import com.mrdotxin.aiturtlesoup.exception.BusinessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) { 
            new BusinessException(ErrorCode.FORBIDDEN_ERROR, "会话错误, 请重试");
        } else {
            return attributes.getRequest();
        }

        return null;
    }   
}
