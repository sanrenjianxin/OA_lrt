package com.lrt.syoa.config.interceptor;

import com.lrt.syoa.common.Constants;
import com.lrt.syoa.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求域中获取role
        String role = (String) request.getAttribute("role");
        // 如果不是管理员角色
        if (!role.equals("admin")) {
            throw new ServiceException(Constants.CODE_401, "您没有管理员权限");
        }
        return true;
    }
}
