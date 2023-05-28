package com.lrt.syoa.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lrt.syoa.common.Constants;
import com.lrt.syoa.entity.User;
import com.lrt.syoa.exception.ServiceException;
import com.lrt.syoa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler){
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "无token,请重新登录");
        }
        // 获取 token 中的userID和角色
        String userId;
        try {
            userId = JWT.decode(token).getClaim("userId").asString();
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token 权限验证失败, 请重新登录");
        }
        // 根据token 中的userID查询数据库
        User user = userService.getById(Integer.parseInt(userId));
        if (user == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在, 请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 验证 token
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token 权限验证失败, 请重新登录");
        }
        // 获取用户的角色
        String role;
        try {
            role = JWT.decode(token).getClaim("role").asString();
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token 用户角色获取失败,请重新登录");
        }
        // 向请求域中放入用户角色数据
        httpServletRequest.setAttribute("role", role);
        return true;
    }
}
